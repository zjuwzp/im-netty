package com.zhss.im.gateway.tcp.dispatcher;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 分发系统实例的管理组件
 */
public class DispatcherInstanceManager {

    /**
     * 分发系统实例地址列表
     */
    private static List<DispatcherInstanceAddress> dispatcherInstanceAddresses =
            new ArrayList<DispatcherInstanceAddress>();

    /**
     * 静态化分发系统实例地址列表
     */
    static {
        dispatcherInstanceAddresses.add(new DispatcherInstanceAddress("localhost", "127.0.0.1", 8090));
    }

    private DispatcherInstanceManager() {

    }

    /**
     * 单例
     */
    static class Singleton {

        static DispatcherInstanceManager instance = new DispatcherInstanceManager();

    }

    /**
     * 获取单例
     * @return
     */
    public static DispatcherInstanceManager getInstance() {
        return Singleton.instance;
    }

    /**
     * 分发系统实例
     */
    private List<DispatcherInstance> dispatcherInstances = new CopyOnWriteArrayList<DispatcherInstance>();

    /**
     * 随机选择一个分发系统实例
     * @return
     */
    public DispatcherInstance chooseDispatcherInstance() {
        Random random = new Random();
        int index = random.nextInt(dispatcherInstances.size());
        return dispatcherInstances.get(index);
    }

    /**
     * 初始化组件
     */
    public void init() {
        // 主动跟一批分发系统建立长连接
        for(DispatcherInstanceAddress dispatcherInstanceAddress : dispatcherInstanceAddresses) {
            try {
                connectDispatcherInstance(dispatcherInstanceAddress);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接一个分发系统实例
     * @param dispatcherInstanceAddress
     */
    private void connectDispatcherInstance(DispatcherInstanceAddress dispatcherInstanceAddress) throws Exception {
        final EventLoopGroup threadGroup  = new NioEventLoopGroup();

        Bootstrap client = new Bootstrap();

        client.group(threadGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                        socketChannel.pipeline().addLast(new DispatcherInstanceHandler());
                    }
                });

        ChannelFuture channelFuture = client.connect(dispatcherInstanceAddress.getIp(), dispatcherInstanceAddress.getPort());

        channelFuture.addListener(new ChannelFutureListener() { // 给异步化的连接请求加入监听器
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()) {
                    DispatcherInstance dispatcherInstance = new DispatcherInstance(
                            (SocketChannel) channelFuture.channel());
                    dispatcherInstances.add(dispatcherInstance);
                    System.out.println("已经跟分发系统建立连接，分发系统地址为：" + channelFuture.channel().remoteAddress());
                } else {
                    channelFuture.channel().close();
                    threadGroup.shutdownGracefully();
                }
            }
        });

        channelFuture.sync();
    }

}
