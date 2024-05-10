package com.wzh.myapp.model.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 75654
 * @date 2024/5/9 11:23
 *
 * 用户登录请求
 */
@Data
public class UserLoginRequest {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}
