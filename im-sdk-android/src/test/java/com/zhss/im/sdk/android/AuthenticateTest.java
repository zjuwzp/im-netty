package com.zhss.im.sdk.android;

public class AuthenticateTest {

    public static void main(String[] args) throws Exception {
        // 在这里应该先请求IM系统的IP List服务，随机获取一台机器的地址

        ImClient imClient = new ImClient();
        imClient.connect("127.0.0.1", 8080);
        imClient.authenticate("test001", "test001_token");

        while(true) {
            Thread.sleep(1000);
        }
    }

}
