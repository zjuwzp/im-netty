package com.zhss.im.common;

public class Constants {

    /**
     * 消息头的长度
     */
    public static final int HEADER_LENGTH = 24;
    /**
     * APP SDK版本号
     */
    public static final int APP_SDK_VERSION_1 = 1;
    /**
     * 消息类型：请求
     */
    public static final int MESSAGE_TYPE_REQUEST = 1;
    /**
     * 消息类型：响应
     */
    public static final int MESSAGE_TYPE_RESPONSE = 2;
    /**
     * 请求类型：用户认证
     */
    public static final int REQUEST_TYPE_AUTHENTICATE = 1;
    /**
     * 消息顺序的默认值
     */
    public static final int SEQUENCE_DEFAULT = 1;
    /**
     * 每条消息的分隔符
     */
    public static final byte[] DELIMITER = "$_".getBytes();
    /**
     * 响应状态：正常
     */
    public static final int RESPONSE_STATUS_OK = 1;
    /**
     * 响应状态：异常
     */
    public static final int RESPONSE_STATUS_ERROR = 2;
    /**
     * 响应异常状态码：未知
     */
    public static final int RESPONSE_ERROR_CODE_UNKNOWN = -1;
    /**
     * 响应异常状态码：认证失败
     */
    public static final int RESPONSE_ERROR_CODE_AUTHENTICATE_FAILURE = 1;
    /**
     * 响应异常状态码：认证时异常
     */
    public static final int RESPONSE_ERROR_CODE_AUTHENTICATE_EXCEPTION = 2;
    /**
     * 响应异常信息
     */
    public static final String RESPONSE_ERROR_MESSAGE_EMPTY = "";

}
