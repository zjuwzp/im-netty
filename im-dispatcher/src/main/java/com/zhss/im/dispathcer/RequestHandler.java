package com.zhss.im.dispathcer;

import com.zhss.im.common.Constants;
import com.zhss.im.protocol.AuthenticateRequestProto;
import com.zhss.im.protocol.AuthenticateResponseProto;

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
     * 处理认证请求
     * @param authenticateRequest
     * @return
     */
    public AuthenticateResponseProto.AuthenticateResponse authenticate(
            AuthenticateRequestProto.AuthenticateRequest authenticateRequest) {
        AuthenticateResponseProto.AuthenticateResponse.Builder builder =
                AuthenticateResponseProto.AuthenticateResponse.newBuilder();
        builder.setUid(authenticateRequest.getUid());
        builder.setToken(authenticateRequest.getToken());
        builder.setTimestamp(authenticateRequest.getTimestamp());

        try {
            String uid = authenticateRequest.getUid();
            String token = authenticateRequest.getToken();
            // 应该请求SSO单点登录系统，把uid和token发送过去，判断是否是登录的用户

            if(authenticateBySSO(uid, token)) {
                builder.setStatus(Constants.RESPONSE_STATUS_OK);
                builder.setErrorCode(Constants.RESPONSE_ERROR_CODE_UNKNOWN);
                builder.setErrorMessage(Constants.RESPONSE_ERROR_MESSAGE_EMPTY);
            } else {
                builder.setStatus(Constants.RESPONSE_STATUS_ERROR);
                builder.setErrorCode(Constants.RESPONSE_ERROR_CODE_AUTHENTICATE_FAILURE);
                builder.setErrorMessage(Constants.RESPONSE_ERROR_MESSAGE_EMPTY);
            }
        } catch(Exception e) {
            // 请求单点登录系统失败
            builder.setStatus(Constants.RESPONSE_STATUS_ERROR);
            builder.setErrorCode(Constants.RESPONSE_ERROR_CODE_AUTHENTICATE_EXCEPTION);
            builder.setErrorMessage(e.toString());
        }

        AuthenticateResponseProto.AuthenticateResponse authenticateResponse = builder.build();

        System.out.println("已经向SSO单点登录系统认证完毕......");

        return authenticateResponse;
    }

    /**
     * 通过单点登录系统进行用户token的认证
     * @param uid
     * @param token
     * @return
     */
    private Boolean authenticateBySSO(String uid, String token) {
        return true;
    }

}
