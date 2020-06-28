package com.zhss.im.gateway.tcp.dispatcher;

import com.zhss.im.common.Constants;
import com.zhss.im.common.Message;
import com.zhss.im.common.Response;
import com.zhss.im.gateway.tcp.SessionManager;
import com.zhss.im.protocol.AuthenticateResponseProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

/**
 * 作为与分发系统通信的客户端的事件处理组件
 */
public class DispatcherInstanceHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        String dispatcherChannelId = channel.remoteAddress().getHostName() + ":"
                + channel.remoteAddress().getPort();
        DispatcherInstanceManager dispatcherInstanceManager = DispatcherInstanceManager.getInstance();
        dispatcherInstanceManager.removeDispatcherInstance(dispatcherChannelId);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = new Message((ByteBuf) msg);

        System.out.println("收到分发系统发送来的消息，消息类型为：" + message.getMessageType());

        if(message.getMessageType() == Constants.MESSAGE_TYPE_RESPONSE) {
            Response response = message.toResponse();

            if(response.getRequestType() == Constants.REQUEST_TYPE_AUTHENTICATE) {
                // 必然是把这个响应消息原封不动的转发给当时发送请求的那个客户端就可以了
                // 如果说认证请求成功了，此时就需要设置本地Session和Redis中的集中式管理的Session
                // 此时我们可以获取到这个响应对应的是哪个uid发送过来的请求的
                // 接着就是根据uid路由找到对应的session
                // 通过uid对应的连接，发送响应回去就可以了

                AuthenticateResponseProto.AuthenticateResponse authenticateResponse =
                        AuthenticateResponseProto.AuthenticateResponse.parseFrom(response.getBody());
                String uid = authenticateResponse.getUid();
                System.out.println("收到分发系统返回的响应：" + authenticateResponse);

                SessionManager sessionManager = SessionManager.getInstance();
                SocketChannel session = sessionManager.getSession(uid);
                session.writeAndFlush(new Response(response, authenticateResponse.toByteArray()).getBuffer());

                System.out.println("将响应发送到客户端，uid=" + uid + "，客户端地址为：" + session);
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
