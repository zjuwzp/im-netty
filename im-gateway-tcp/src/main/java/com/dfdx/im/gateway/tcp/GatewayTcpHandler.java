package com.dfdx.im.gateway.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class GatewayTcpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("跟客户端完成连接：" + ctx.channel().remoteAddress().toString());
    }

    /**
     * 跟某个客户端的连接断开了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        NettyChannelManager nettyChannelManager  = NettyChannelManager.getInstance();
        nettyChannelManager.removeChannel(socketChannel);

        System.out.println("检测到客户端的连接断开，删除其连接缓存：" + socketChannel.remoteAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ctx.channel(); -> 对应的客户端的SocketChannel
        // 一旦token认证完毕之后，就应该把这个客户端的SocketChannel给缓存起来
        // 后面如果有需要对这个客户端推送一个消息过去，直接从缓存里面找到这个SocketChannel，进行推送就可以了
        // 此时服务端就可以主动把消息发送给客户端了
        NettyChannelManager nettyChannelManager = NettyChannelManager.getInstance();

        String message = (String) msg;

        System.out.println("接收到一条消息：" + message);

        if(message.startsWith("发起用户认证")) {
            String token = message.split("\\|")[2];
            // 使用token去找SSO单点登录系统进行认证，看这个用户是否合法的登录用户

            // 如果认证成功的话，就可以把这个连接缓存起来了
            String userId = message.split("\\|")[1];
            nettyChannelManager.addChannel(userId, (SocketChannel) ctx.channel());

            System.out.println("对用户发起的认证确认完毕，缓存客户端长连接，userId=" + userId);
        } else {
            String userId = message.split("\\|")[1];;

            if(!nettyChannelManager.existChannel(userId)) {
                System.out.println("未认证用户，不能处理请求");

                byte[] responseBytes = "未认证用户，不能处理请求$_".getBytes();
                ByteBuf responseBuffer = Unpooled.buffer(responseBytes.length);
                responseBuffer.writeBytes(responseBytes);
                ctx.writeAndFlush(responseBuffer);
            } else {
                System.out.println("将消息分发到Kafka中去：" + message);
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
