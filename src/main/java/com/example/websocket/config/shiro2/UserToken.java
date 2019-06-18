package com.hanren.admin.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {

	// 企业端登录 政府端登录
	private String loginType;

	public UserToken(final String username, final String password, String loginType) {
		super(username, password);
		this.loginType = loginType;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

}
