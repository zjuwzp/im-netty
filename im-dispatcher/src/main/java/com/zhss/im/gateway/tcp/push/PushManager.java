package com.zhss.im.gateway.tcp.push;

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
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
