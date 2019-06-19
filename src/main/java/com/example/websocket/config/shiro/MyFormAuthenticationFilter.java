package com.example.websocket.config.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;


public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
			httpResponse.setHeader("Access-Control-Allow-Methods", "PUT,DELETE,OPTIONS,POST,GET");
			httpResponse.setHeader("Access-Control-Allow-Headers",
					httpRequest.getHeader("Access-Control-Request-Headers"));
			httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
			httpResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (isAjax(request)) {
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json");
			httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
		} else {
			// saveRequestAndRedirectToLogin(request, response);
			/**
			 * @Mark 非ajax请求重定向为登录页面
			 */
			httpServletResponse.sendRedirect("/login");
		}
		return false;

	}

	private boolean isAjax(ServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
