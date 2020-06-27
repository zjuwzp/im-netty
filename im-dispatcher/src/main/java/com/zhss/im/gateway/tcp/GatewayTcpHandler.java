package com.zhss.im.gateway.tcp;

import com.zhss.im.common.Constants;
import com.zhss.im.common.Message;
import com.zhss.im.common.Request;
import com.zhss.im.protocol.AuthenticateRequestProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class GatewayTcpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已经跟客户端建立连接，客户端地址为：" + ctx.channel());
    }

    /**
     * 跟某个客户端的连接断开了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.removeSession(socketChannel);

        System.out.println("检测到客户端的连接断开，删除其连接缓存：" + socketChannel.remoteAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ctx.channel(); -> 对应的客户端的SocketChannel
        // 一旦token认证完毕之后，就应该把这个客户端的SocketChannel给缓存起来
        // 后面如果有需要对这个客户端推送一个消息过去，直接从缓存里面找到这个SocketChannel，进行推送就可以了
        // 此时服务端就可以主动把消息发送给客户端了

        // 获取请求处理组件
        RequestHandler requestHandler = RequestHandler.getInstance();

        // 解析收到的请求
        Message message = new Message((ByteBuf) msg);
        System.out.println("收到一个消息，消息类型为：" + message.getMessageType());

        // 根据请求的类型判断要走什么处理逻辑
        // 如果是认证请求
        if(message.getMessageType() == Constants.MESSAGE_TYPE_REQUEST) {
            Request request = message.toRequest();

            if(request.getRequestType() == Constants.REQUEST_TYPE_AUTHENTICATE) {
                // 将消息体反序列化为认证请求
                AuthenticateRequestProto.AuthenticateRequest authenticateRequest =
                        AuthenticateRequestProto.AuthenticateRequest.parseFrom(request.getBody());
                System.out.println("收到客户端发送过来的认证请求：" + authenticateRequest);
                // 调用业务逻辑处理组件进行认证
                requestHandler.authenticate(authenticateRequest);
                // 设置一下本地Session，维护uid和session映射关系，维护channelId和uid的关系
                SessionManager sessionManager = SessionManager.getInstance();
                sessionManager.addSession(authenticateRequest.getUid(), (SocketChannel) ctx.channel());
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
