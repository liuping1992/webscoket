package com.example.websocket.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.websocket.main_part.entity.User;
import com.example.websocket.main_part.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class EnterpriseShiroRealm  extends AuthorizingRealm  {

	@Autowired
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		if (username == null) {
			throw new UnknownAccountException();
		}
		User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername,username));
		if(null == user) {
			throw new UnknownAccountException(); //如果用户名错误
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(),getName());
		return authenticationInfo;
	}

}
