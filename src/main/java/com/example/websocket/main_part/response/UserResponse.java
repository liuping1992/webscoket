package com.example.websocket.main_part.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lp
 * @since 2019-06-13
 */
@Data
@Accessors(chain = true)
public class UserResponse {

    private Long id;

    /**
     * 用户名/账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 头像
     */
    private String head;

    /**
     *
     */
    private String token;

}
