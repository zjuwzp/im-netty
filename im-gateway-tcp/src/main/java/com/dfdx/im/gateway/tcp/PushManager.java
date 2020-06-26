package com.dfdx.im.gateway.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;

/**
 * 消息推送管理组件
 */
public class PushManager {

    /**
     * 启动消息推送组件
     */
    public void start() {
        new PushThread().start();
    }

    /**
     * 消息推送线程
     */
    class PushThread extends Thread {

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(60 * 1000);

                    String testUserId = "test002";

                    NettyChannelManager nettyChannelManager = NettyChannelManager.getInstance();
                    SocketChannel socketChannel = nettyChannelManager.getChannel(testUserId);

                    if(socketChannel != null) {
                        byte[] messageBytes = "test001发送过来的消息$_".getBytes();
                        ByteBuf messageBuffer = Unpooled.buffer(messageBytes.length);
                        messageBuffer.writeBytes(messageBytes);
                        socketChannel.writeAndFlush(messageBuffer);

                        System.out.println("给客户端反向推送消息：userId=" + testUserId + "，socketChannel=" + socketChannel);
                    } else {
                        System.out.println("目标用户已经下线，等待其上线之后再推送");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
