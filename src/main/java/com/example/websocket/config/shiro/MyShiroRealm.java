package com.example.websocket.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.websocket.main_part.entity.User;
import com.example.websocket.main_part.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: energy-system-dev
 * @description: MyShiroRealm
 * @author: Thomas.Yang
 * @create: 2019-03-25 09:45
 **/
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername,username));
        if(null == user) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }
}
