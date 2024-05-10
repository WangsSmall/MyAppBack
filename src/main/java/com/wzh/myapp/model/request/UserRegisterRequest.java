package com.wzh.myapp.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 75654
 * @date 2024/5/9 11:23
 *
 * 用户注册请求
 */
@Data
public class UserRegisterRequest implements Serializable {
    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Object gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 自己编号
     */
    private String myCode;

    /**
     * 标签列表 Varchar
     */
    private String tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
