package com.zhss.im.gateway.tcp;

import com.zhss.im.gateway.tcp.dispatcher.DispatcherInstanceManager;
import com.zhss.im.gateway.tcp.push.PushManager;
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
        System.out.println("TCP接入系统开始启动......");

        // 启动消息推送组件
        PushManager pushManager = new PushManager();
        pushManager.start();

        // 启动分发系统实例管理组件
        DispatcherInstanceManager dispatcherInstanceManager = DispatcherInstanceManager.getInstance();
        dispatcherInstanceManager.init();

        // 启动Netty服务器
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
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(4096, delimiter));
                            socketChannel.pipeline().addLast(new GatewayTcpHandler());
                        }

                    });

            ChannelFuture channelFuture = server.bind(PORT).sync();

            System.out.println("TCP接入系统已经启动......");

            channelFuture.channel().closeFuture().sync();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            connectionThreadGroup.shutdownGracefully();
            ioThreadGroup.shutdownGracefully();
        }
    }

}
