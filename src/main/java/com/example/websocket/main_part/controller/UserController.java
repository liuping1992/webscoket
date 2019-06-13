package com.example.websocket.main_part.controller;

import com.example.websocket.main_part.BaseRequest;
import com.example.websocket.main_part.request.UserRequest;
import com.example.websocket.main_part.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lp
 * @since 2019-06-11
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/main_part/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增用户",notes = "用于用户的新增")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity add(@Validated(UserRequest.Add.class) @RequestBody UserRequest userRequest){
        userService.add(userRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "修改用户",notes = "用于用户的信息修改")
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public ResponseEntity modify(@Validated(UserRequest.Update.class) @RequestBody UserRequest userRequest){
        userService.modify(userRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@Validated(UserRequest.Login.class) @RequestBody UserRequest userRequest) throws Exception {
        return new ResponseEntity(userService.login(userRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public void error() throws Exception {
        throw new UnauthenticatedException();
    }
}

