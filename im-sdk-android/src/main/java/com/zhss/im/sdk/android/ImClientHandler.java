package com.zhss.im.sdk.android;

import com.zhss.im.common.Constants;
import com.zhss.im.common.Message;
import com.zhss.im.common.Response;
import com.zhss.im.protocol.AuthenticateResponseProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
        // 服务端发送过来的消息就是在这里收到的
        Message message = new Message((ByteBuf) msg);

        System.out.println("收到TCP接入系统发送过来的消息，消息类型为：" + message.getMessageType());

        if(message.getMessageType() == Constants.MESSAGE_TYPE_RESPONSE) {
            Response response = message.toResponse();

            if(response.getRequestType() == Constants.REQUEST_TYPE_AUTHENTICATE) {
                AuthenticateResponseProto.AuthenticateResponse authenticateResponse =
                        AuthenticateResponseProto.AuthenticateResponse.parseFrom(response.getBody());
                System.out.println("认证请求收到响应：" + authenticateResponse);
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
