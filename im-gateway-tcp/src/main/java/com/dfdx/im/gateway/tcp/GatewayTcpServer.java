package com.dfdx.im.gateway.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class GatewayTcpServer {

    public static final int PORT = 8080;

    public static void main(String[] args) {
        PushManager pushManager = new PushManager();
        pushManager.start();

        EventLoopGroup connectionThreadGroup = new NioEventLoopGroup();
        EventLoopGroup ioThreadGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();

            server.group(connectionThreadGroup, ioThreadGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            socketChannel.pipeline().addLast(new StringDecoder());     //会把收到的消息直接转为String，而不是ByteBuf
                            socketChannel.pipeline().addLast(new GatewayTcpHandler());
                        }

                    });

            ChannelFuture channelFuture = server.bind(PORT).sync();

            channelFuture.channel().closeFuture().sync();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionThreadGroup.shutdownGracefully();
            ioThreadGroup.shutdownGracefully();
        }
    }

}
