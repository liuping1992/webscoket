package com.example.websocket.main_part.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lp
 * @since 2019-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

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


}
