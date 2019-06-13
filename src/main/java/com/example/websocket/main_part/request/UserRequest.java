package com.example.websocket.main_part.request;

import com.example.websocket.main_part.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRequest extends BaseRequest {

    @NotNull(groups = {Update.class,Delete.class})
    private Long id;

    /**
     * 用户名/账号
     */
    @NotBlank(groups = {Add.class,Login.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(groups = {Add.class,Login.class})
    private String password;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 头像
     */
    private String head;

    public interface Login{

    }

}
