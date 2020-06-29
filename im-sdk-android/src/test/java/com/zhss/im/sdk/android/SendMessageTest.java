package com.zhss.im.sdk.android;

public class SendMessageTest {

    public static void main(String[] args) throws Exception {
        ImClient imClient = new ImClient();
        imClient.connect("127.0.0.1", 8080);
        imClient.authenticate("test002", "test002_token");
        imClient.sendMessage("test002", "test002", "hello world");

        while(true) {
            Thread.sleep(1000);
        }
    }

}
