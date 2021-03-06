package com.example.websocket.config.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) {
		ModelAndView mv = new ModelAndView();  
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();  
        if (ex instanceof UnauthenticatedException) {  
            attributes.put("code", "1000001");  
            attributes.put("msg", "token错误");  
        } else if (ex instanceof UnauthorizedException) {  
            attributes.put("code", "1000002");  
            attributes.put("msg", "用户无权限");  
        } else {  
            attributes.put("code", "1000003");
            ex.printStackTrace();
            attributes.put("msg", ex.getMessage());  
        }  
  
        view.setAttributesMap(attributes);  
        mv.setView(view);  
        return mv;
	}
	

}
