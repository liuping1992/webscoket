package com.example.websocket.main_part.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.websocket.config.shiro.UserToken;
import com.example.websocket.main_part.entity.User;
import com.example.websocket.main_part.dao.UserMapper;
import com.example.websocket.main_part.request.UserRequest;
import com.example.websocket.main_part.response.UserResponse;
import com.example.websocket.main_part.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lp
 * @since 2019-06-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void add(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        super.save(user);
    }

    @Override
    public void modify(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        super.updateById(user);
    }

    @Override
    public UserResponse login(UserRequest userRequest) throws Exception{
        Subject subject = SecurityUtils.getSubject();
        UserToken token = new UserToken(userRequest.getUsername(), userRequest.getPassword(), "Enterprise");
        subject.login(token);
        User user = super.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername,userRequest.getUsername()));
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user,response);
        response.setToken(subject.getSession().getId().toString());
        return response;
    }
}
