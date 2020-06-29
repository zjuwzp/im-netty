package com.zhss.im.dispathcer;

import com.alibaba.fastjson.JSONObject;
import com.zhss.im.common.Constants;
import com.zhss.im.common.Message;
import com.zhss.im.common.Request;
import com.zhss.im.common.Response;
import com.zhss.im.protocol.AuthenticateRequestProto;
import com.zhss.im.protocol.AuthenticateResponseProto;
import com.zhss.im.protocol.MessagePushResponseProto;
import com.zhss.im.protocol.MessageSendRequestProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import redis.clients.jedis.Jedis;

/**
 * 分发系统Netty服务器事件处理类
 */
public class DispatcherHandler extends ChannelInboundHandlerAdapter {

    /**
     * 一个接入系统跟分发系统建立了连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        String channelId = channel.remoteAddress().getHostName() + ":" +
                channel.remoteAddress().getPort();
        GatewayInstanceManager gatewayInstanceManager = GatewayInstanceManager.getInstance();
        gatewayInstanceManager.addGatewayInstance(channelId, channel);
        System.out.println("已经跟TCP接入系统建立连接，TCP接入系统地址为：" + channel.remoteAddress());
    }

    /**
     * 一个接入系统跟分发系统的连接断开了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        String channelId = channel.remoteAddress().getHostName() + ":" +
                channel.remoteAddress().getPort();
        GatewayInstanceManager gatewayInstanceManager = GatewayInstanceManager.getInstance();
        gatewayInstanceManager.removeGatewayInstance(channelId);
        System.out.println("跟TCP接入系统的连接断开，地址为：" + channel);
    }

    /**
     * 接收到一个接入系统发送过来的请求
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = new Message((ByteBuf) msg);

        if(message.getMessageType() == Constants.MESSAGE_TYPE_REQUEST) {
            Request request = message.toRequest();

            if(request.getRequestType() == Constants.REQUEST_TYPE_AUTHENTICATE) {
                authenticate(ctx, request);
            } else if(request.getRequestType() == Constants.REQUEST_TYPE_SEND_MESSAGE) {
                System.out.println("收到一条单聊消息......");
                sendMessage(ctx, request);
            }
        } else if(message.getMessageType() == Constants.MESSAGE_TYPE_RESPONSE) {
            Response response = message.toResponse();

            if(response.getRequestType() == Constants.REQUEST_TYPE_PUSH_MESSAGE) {
                pushMessageResponse(response);
            }
        }
    }

    /**
     * 推送消息的响应
     * @param response
     */
    private void pushMessageResponse(Response response) throws Exception {
        MessagePushResponseProto.MessagePushResponse messagePushResponse =
                MessagePushResponseProto.MessagePushResponse.parseFrom(response.getBody());

        JSONObject json = new JSONObject();
        json.put("messageId", messagePushResponse.getMessageId());

        KafkaManager kafkaManager = KafkaManager.getInstance();
        KafkaProducer producer = kafkaManager.getProducer();

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "push_message_response", json.toJSONString());

        producer.send(record);

        System.out.println("将消息推送的响应写入Kafka：" + json.toJSONString());
    }

    /**
     * 认证
     * @param ctx
     * @param request
     * @throws Exception
     */
    private void authenticate(ChannelHandlerContext ctx, Request request) throws Exception {
        RequestHandler requestHandler = RequestHandler.getInstance();

        // 在这里应该是去找SSO单点登录系统去对用户的token进行认证
        AuthenticateRequestProto.AuthenticateRequest authenticateRequest =
                AuthenticateRequestProto.AuthenticateRequest.parseFrom(request.getBody());
        System.out.println("收到TCP接入系统发送的认证请求：" + authenticateRequest);

        AuthenticateResponseProto.AuthenticateResponse authenticateResponse =
                requestHandler.authenticate(authenticateRequest);

        if(authenticateResponse.getStatus() == Constants.RESPONSE_STATUS_OK) {
            SocketChannel socketChannel = (SocketChannel) ctx.channel();
            String gatewayChannelId = socketChannel.remoteAddress().getHostName() + ":"
                    + socketChannel.remoteAddress().getPort();

            // 其实在这里应该把session信息写入Redis的
            String sessionKey = "session_" + authenticateRequest.getUid();
            String sessionValue= "{"
                    + "'token':'" + authenticateRequest.getToken() + "',"
                    + "'timestamp':" + authenticateRequest.getTimestamp() + ","
                    + "'isAuthenticated':'true',"
                    + "'authenticateTimestamp':" + System.currentTimeMillis() + ","
                    + "'gatewayChannelId': '" + gatewayChannelId + "'"
                    + "}";

            JedisManager jedisManager = JedisManager.getInstance();
            Jedis jedis = jedisManager.getJedis();
            jedis.set(sessionKey, sessionValue);

            System.out.println("在Redis中写入分布式Session......");
        }

        Response response = new Response(request, authenticateResponse.toByteArray());
        ctx.writeAndFlush(response.getBuffer());

        System.out.println("返回响应给TCP接入系统：" + authenticateResponse);
    }

    /**
     * 发送单聊消息
     * @param ctx
     * @param message
     */
    private void sendMessage(ChannelHandlerContext ctx, Message message) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        String gatewayChannelId = channel.remoteAddress().getHostName() + ":" +
                channel.remoteAddress().getPort();

        Request request = message.toRequest();

        MessageSendRequestProto.MessageSendRequest messageSendRequest =
                MessageSendRequestProto.MessageSendRequest.parseFrom(request.getBody());

        JSONObject json = new JSONObject();
        json.put("senderId", messageSendRequest.getSenderId());
        json.put("receiverId", messageSendRequest.getReceiverId());
        json.put("content", messageSendRequest.getContent());
        json.put("requestType", request.getRequestType());
        json.put("sequence", message.getSequence());
        json.put("gatewayChannelId", gatewayChannelId);

        KafkaManager kafkaManager = KafkaManager.getInstance();
        KafkaProducer producer = kafkaManager.getProducer();

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                "send_message", json.toJSONString());

        // 咱们，在启动kafka之后，把需要的四个topic都创建好
        // send_message，send_message_response，push_message，push_message_response

        producer.send(record);

        System.out.println("将单聊消息写入Kafka中，" + json.toJSONString());
    }

    /**
     * 处理完毕一个请求
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 发生异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
