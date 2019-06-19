package com.example.websocket.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class GovernmentShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
//		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//
//		Set<String> rolename = new HashSet<String>();
//
//		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		String username = principal.getPrimaryPrincipal().toString();
//		User user = new User();
//		user.setUsername(username);
//		user = userMapper.getUserByUserName(user);
//		// 获取用户角色
//		Set<Role> roles = new HashSet<Role>();
//		roles = roleMapper.findRoles(user.getId());
//		Set<String> pers = new HashSet<String>();
//		for (Role role : roles) {
//			System.out.println(role);
//			rolename.add(role.getName());
//			Set<String> permissions = accessService.getAccessByRoleId(role.getId());
//			for(String p : permissions) {
//				pers.add(p);
//			}
//		}
//		authorizationInfo.setRoles(rolename);
//		authorizationInfo.setStringPermissions(pers);
//		return authorizationInfo;
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//		String username = (String) token.getPrincipal();
//		if (username == null) {
//			throw new UnknownAccountException();
//		}
//		User user = new User();
//		user.setUsername(username);
//		User userInfo = userMapper.getUserByUserName(user);
//		if (userInfo == null) {
//			throw new UnknownAccountException();
//		}
//		if (userInfo.getStatus() == 0) {
//			throw new LockedAccountException();
//		}
//		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, userInfo.getPassword(),
//				getName());
//		return authenticationInfo;
		return null;
	}

}
