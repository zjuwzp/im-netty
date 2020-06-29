package com.zhss.im.dispathcer;

import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接入系统实例管理组件
 */
public class GatewayInstanceManager {

    private GatewayInstanceManager() {

    }

    /**
     * 单例类
     */
    static class Singleton {

        private static GatewayInstanceManager instance = new GatewayInstanceManager();

    }

    /**
     * 获取单例
     * @return
     */
    public static GatewayInstanceManager getInstance() {
        return Singleton.instance;
    }

    /**
     * 接入系统的实例列表
     */
    private Map<String, SocketChannel> gatewayInstances =
            new ConcurrentHashMap<String, SocketChannel>();

    /**
     * 添加一个接入系统实例
     * @param channelId 网络连接id
     * @param channel 网络连接
     */
    public void addGatewayInstance(String channelId, SocketChannel channel) {
        gatewayInstances.put(channelId, channel);
    }

    /**
     * 删除一个接入系统实例
     * @param channelId 网络连接id
     */
    public void removeGatewayInstance(String channelId) {
        gatewayInstances.remove(channelId);
    }

    /**
     * 获取接入系统实例
     * @param channelId
     * @return
     */
    public SocketChannel getGatewayInstance(String channelId) {
        return gatewayInstances.get(channelId);
    }

}
