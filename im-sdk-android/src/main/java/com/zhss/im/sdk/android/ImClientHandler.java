package com.zhss.im.sdk.android;

import com.zhss.im.common.Constants;
import com.zhss.im.common.Message;
import com.zhss.im.common.Request;
import com.zhss.im.common.Response;
import com.zhss.im.protocol.AuthenticateResponseProto;
import com.zhss.im.protocol.MessagePushRequestProto;
import com.zhss.im.protocol.MessagePushResponseProto;
import com.zhss.im.protocol.MessageSendResponseProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class ImClientHandler extends ChannelInboundHandlerAdapter {

    private ImClient client;

    public ImClientHandler(ImClient client) {
        this.client = client;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.reconnect();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel gatewayInstance = (SocketChannel) ctx.channel();

        // 服务端发送过来的消息就是在这里收到的
        Message message = new Message((ByteBuf) msg);

        System.out.println("收到TCP接入系统发送过来的消息，消息类型为：" + message.getMessageType());

        if(message.getMessageType() == Constants.MESSAGE_TYPE_RESPONSE) {
            Response response = message.toResponse();

            if(response.getRequestType() == Constants.REQUEST_TYPE_AUTHENTICATE) {
                AuthenticateResponseProto.AuthenticateResponse authenticateResponse =
                        AuthenticateResponseProto.AuthenticateResponse.parseFrom(response.getBody());
                System.out.println("认证请求收到响应：" + authenticateResponse);
                this.client.connected();
            } else if(response.getRequestType() == Constants.REQUEST_TYPE_SEND_MESSAGE) {
                MessageSendResponseProto.MessageSendResponse messageSendResponse =
                        MessageSendResponseProto.MessageSendResponse.parseFrom(response.getBody());
                System.out.println("客户端收到单聊消息的响应，" +
                        "messageId=" + messageSendResponse.getMessageId());
            }
        } else if(message.getMessageType() == Constants.MESSAGE_TYPE_REQUEST) {
            Request request = message.toRequest();

            if(request.getRequestType() == Constants.REQUEST_TYPE_PUSH_MESSAGE) {
                MessagePushRequestProto.MessagePushRequest messagePushRequest =
                        MessagePushRequestProto.MessagePushRequest.parseFrom(request.getBody());
                System.out.println("接收到一条消息推送：" + messagePushRequest);

                MessagePushResponseProto.MessagePushResponse.Builder builder =
                        MessagePushResponseProto.MessagePushResponse.newBuilder();
                builder.setMessageId(messagePushRequest.getMessageId());
                MessagePushResponseProto.MessagePushResponse messagePushResponse =
                        builder.build();

                Response response = new Response(
                        request,
                        messagePushResponse.toByteArray()
                );

                gatewayInstance.writeAndFlush(response.getBuffer());

                System.out.println("返回消息推送的响应给接入系统：" + messagePushResponse);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
