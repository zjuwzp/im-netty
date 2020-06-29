package com.zhss.im.gateway.tcp;

import com.zhss.im.common.Constants;
import com.zhss.im.common.Request;
import com.zhss.im.common.Response;
import com.zhss.im.gateway.tcp.dispatcher.DispatcherInstance;
import com.zhss.im.gateway.tcp.dispatcher.DispatcherInstanceManager;
import com.zhss.im.protocol.*;
import io.netty.channel.socket.SocketChannel;

/**
 * 请求处理组件
 */
public class RequestHandler {

    private RequestHandler() {

    }

    static class Singleton {

        static RequestHandler instance = new RequestHandler();

    }

    public static RequestHandler getInstance() {
        return Singleton.instance;
    }

    /**
     * 认证请求处理逻辑
     * @return
     */
    public void authenticate(
            AuthenticateRequestProto.AuthenticateRequest authenticateRequest) {
        // 主要是继续将请求发送给分发系统
        // 封装好完整的请求

        // 随机从分发系统的多个实例中获取一个出来
        DispatcherInstanceManager dispatcherInstanceManager = DispatcherInstanceManager.getInstance();
        DispatcherInstance dispatcherInstance = dispatcherInstanceManager.chooseDispatcherInstance();
        dispatcherInstance.authenticate(authenticateRequest);

        System.out.println("向随机挑选的分发系统（地址为：" + dispatcherInstance.getSocketChannel() + "）发送请求，" +
                "请求大小为：" + authenticateRequest.toByteArray().length);

        // 这里一旦认证成功了之后，就需要去维护Session了，也就是说跟一个客户端的连接建立起来了
        // 一方面在自己本地内存里维护Session，一方面去Redis里写入集中式管理的Session
    }

    /**
     * 发送单聊消息
     * @param request
     */
    public void sendMessage(Request request) {
        DispatcherInstanceManager dispatcherInstanceManager =
                DispatcherInstanceManager.getInstance();
        DispatcherInstance dispatcherInstance =
                dispatcherInstanceManager.chooseDispatcherInstance();
        dispatcherInstance.sendMessage(request);

        System.out.println("向随机挑选的分发系统（地址为：" +
                dispatcherInstance.getSocketChannel() + "）发送单聊消息请求");
    }

    /**
     * 发送消息推送的响应
     * @param response
     */
    public void pushMessageResponse(Response response) {
        DispatcherInstanceManager dispatcherInstanceManager =
                DispatcherInstanceManager.getInstance();
        DispatcherInstance dispatcherInstance =
                dispatcherInstanceManager.chooseDispatcherInstance();
        dispatcherInstance.pushMessageResponse(response);

        System.out.println("向随机挑选的分发系统（" +
                "地址为：" + dispatcherInstance.getSocketChannel() + "）发送消息推送的响应");
    }

}
