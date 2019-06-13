package com.example.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @program: energy-system
 * @description: 异常统一处理
 * @author: Thomas.Yang
 * @create: 2018-10-31 08:37
 **/

@RestControllerAdvice
@Slf4j
public class SystemExceptionHandler {
    private static Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");


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
        HttpStatus httpStatus = HttpStatus.OK;
        ErrorMsg errorMsg = null;
        if (e instanceof UnknownAccountException){
            httpStatus = HttpStatus.CONFLICT;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_LOGIN.getErrorCode()
                    , ErrorEnum.ERROR_LOGIN.getErrorMessage());
        }
        // 未授权认证
        else if(e instanceof UnauthenticatedException){
            httpStatus = HttpStatus.UNAUTHORIZED;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_NEED_AUTH.getErrorCode()
                    , ErrorEnum.ERROR_LOGIN.getErrorMessage());
        }
        else {
            httpStatus = HttpStatus.UNAUTHORIZED;
            errorMsg = new ErrorMsg(ErrorEnum.ERROR_NEED_AUTH.getErrorCode()
                    , ErrorEnum.ERROR_LOGIN.getErrorMessage());
        }

        return new ResponseEntity<>(errorMsg, httpStatus);

    }

}
