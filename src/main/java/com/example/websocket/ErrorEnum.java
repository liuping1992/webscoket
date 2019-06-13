package com.example.websocket;

/**
 * @program: energy-system
 * @description: 自定义错误码
 * @author: Thomas.Yang
 * @create: 2018-09-28 19:17
 **/
public enum ErrorEnum {
    //
    ERROR_STOMP("ERROR_STOMP","STOMP协议不正确。"),
    ERROR_LOGIN("ERROR_LOGIN","用户名或密码不正确"),
    ERROR_NEED_AUTH("ERROR_NEED_AUTH","需要授权认证"),
    ERROR_PERMISSION_DENIED("ERROR_PERMISSION_DENIED","权限不足"),
    ERROR_VALID("ERROR_VALID","参数验证失败"),
    ERROR_PARAM("ERROR_PARAM","参数不正确"),
    ERROR_BIZ("ERROR_BIZ","业务逻辑处理出错"),
    ;

    private final String errorCode;
    private final String errorMessage;



    ErrorEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
