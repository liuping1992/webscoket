package com.example.websocket.main_part.service;

import com.example.websocket.main_part.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.websocket.main_part.request.UserRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lp
 * @since 2019-06-13
 */
public interface IUserService extends IService<User> {

    void add(UserRequest userRequest);

}
