package com.example.websocket.main_part.service.impl;

import com.example.websocket.main_part.entity.User;
import com.example.websocket.main_part.dao.UserMapper;
import com.example.websocket.main_part.request.UserRequest;
import com.example.websocket.main_part.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        System.out.println(user);
    }
}
