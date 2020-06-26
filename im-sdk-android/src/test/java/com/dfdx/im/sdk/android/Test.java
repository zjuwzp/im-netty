package com.dfdx.im.sdk.android;

public class Test {

    public static void main(String[] args) throws Exception {
        // 在这里应该先请求IM系统的IP List服务，随机获取一台机器的地址

        ImClient imClient = new ImClient();
        imClient.connect("127.0.0.1", 8080);
        imClient.authenticate("test002", "test002_token");
//        imClient.send("test001", "test001发送过来的消息");

        while(true) {
            Thread.sleep(1000);
        }
    }

}
