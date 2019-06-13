package com.example.websocket;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: energy-system
 * @description: 异常统一处理
 * @author: Thomas.Yang
 * @create: 2018-10-31 08:37
 **/

@RestControllerAdvice
public class SystemExceptionHandler {

    /**
     * 系统异常处理
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity defaultErrorHandler(HttpServletRequest req, Exception e) {

        HttpStatus httpStatus = null;
        ErrorMsg errorMsg = null;
        // 业务逻辑错误
        if (e instanceof BizException) {
            httpStatus = HttpStatus.CONFLICT;
            BizException bizException = (BizException) e;
            errorMsg = new ErrorMsg(bizException.getError().getErrorCode(), bizException.getMessage());
        }
        // 用户名错误
        else if (e instanceof UnknownAccountException){
            httpStatus = HttpStatus.CONFLICT;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_LOGIN);
        }
        // 密码错误
        else if (e instanceof IncorrectCredentialsException){
            httpStatus = HttpStatus.CONFLICT;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_LOGIN);
        }
        // 未授权认证
        else if(e instanceof UnauthenticatedException){
            httpStatus = HttpStatus.UNAUTHORIZED;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_NEED_AUTH);
        }
        // 权限不足
        else if(e instanceof UnauthorizedException){
            httpStatus = HttpStatus.FORBIDDEN;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_PERMISSION_DENIED);
        }
        // 请求不存在
        else if (e instanceof NoHandlerFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            errorMsg = new ErrorMsg(String.valueOf(httpStatus.value()), "请求的资源不存在");
        }
        // 消息不可读
        else if (e instanceof HttpMessageNotReadableException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_VALID.getErrorCode(), ErrorEnum.ERROR_VALID.getErrorMessage());
        }
        // 参数校验异常
        else if (e instanceof MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder();
            ((MethodArgumentNotValidException) e).getBindingResult()
                    .getAllErrors().forEach(x -> sb.append(x.getDefaultMessage()).append(","));
            String strErrMsg = sb.toString();
            strErrMsg = strErrMsg.length() == 0 ? "" : strErrMsg.substring(0, strErrMsg.lastIndexOf(","));
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_VALID.getErrorCode(), strErrMsg);
        }
        // 参数类型异常
        else if (e instanceof MethodArgumentTypeMismatchException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_PARAM);
        }
        // 其他异常
        else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMsg = new ErrorMsg(String.valueOf(httpStatus.value()), "系统错误:"+e.getLocalizedMessage());
        }

        return new ResponseEntity<>(errorMsg, httpStatus);

    }

}
