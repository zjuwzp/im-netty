package com.zhss.im.sdk.android;

import com.zhss.im.protocol.AuthenticateRequestProto;

public class ProtobufTest {

    public static void main(String[] args) throws Exception {
        AuthenticateRequestProto.AuthenticateRequest authenticateRequest = createAuthenticateRequest();

        // 测试序列化
        byte[] serializedAuthenticateRequest =  authenticateRequest.toByteArray();
        System.out.println(serializedAuthenticateRequest.length);

        // 测试反序列化
        AuthenticateRequestProto.AuthenticateRequest deserializedAuthenticateRequest =
                AuthenticateRequestProto.AuthenticateRequest.parseFrom(serializedAuthenticateRequest);
        System.out.println(deserializedAuthenticateRequest);
    }

    private static AuthenticateRequestProto.AuthenticateRequest createAuthenticateRequest() {
        AuthenticateRequestProto.AuthenticateRequest.Builder builder =
                AuthenticateRequestProto.AuthenticateRequest.newBuilder();
        builder.setUid("test_user_001");
        builder.setToken("test_user_001_token");
        builder.setTimestamp(System.currentTimeMillis());
        return builder.build();
}

}
