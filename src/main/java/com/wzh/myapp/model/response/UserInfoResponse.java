package com.wzh.myapp.model.response;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 75654
 * @date 2024/5/10 14:47
 */
public class UserInfoResponse implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号唯一，昵称不唯一
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
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户角色 0-普通用户 1-管理员
     */
    private Integer userRole;

    /**
     * 自己编号
     */
    private String myCode;

    /**
     * 标签列表 Varchar
     */
    private String tags;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
