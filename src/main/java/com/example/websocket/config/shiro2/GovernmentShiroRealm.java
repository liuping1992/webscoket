package com.hanren.admin.config.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanren.admin.entity.Role;
import com.hanren.admin.entity.User;
import com.hanren.admin.mapper.RoleMapper;
import com.hanren.admin.mapper.UserMapper;
import com.hanren.admin.service.AccessService;



public class GovernmentShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private AccessService accessService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");

		Set<String> rolename = new HashSet<String>();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String username = principal.getPrimaryPrincipal().toString();
		User user = new User();
		user.setUsername(username);
		user = userMapper.getUserByUserName(user);
		// 获取用户角色
		Set<Role> roles = new HashSet<Role>();
		roles = roleMapper.findRoles(user.getId());
		Set<String> pers = new HashSet<String>();
		for (Role role : roles) {
			System.out.println(role);
			rolename.add(role.getName());
			Set<String> permissions = accessService.getAccessByRoleId(role.getId());
			for(String p : permissions) {
				pers.add(p);
			}
		}
		authorizationInfo.setRoles(rolename);
		authorizationInfo.setStringPermissions(pers);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		if (username == null) {
			throw new UnknownAccountException();
		}
		User user = new User();
		user.setUsername(username);
		User userInfo = userMapper.getUserByUserName(user);
		if (userInfo == null) {
			throw new UnknownAccountException();
		}
		if (userInfo.getStatus() == 0) {
			throw new LockedAccountException();
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, userInfo.getPassword(),
				getName());
		return authenticationInfo;
	}

}
