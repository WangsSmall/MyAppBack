package com.wzh.myapp.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 照片集表
 * @TableName user_photo
 */
@TableName(value ="user_photo")
@Data
public class UserPhoto implements Serializable {
    /**
     * 照片集ID
     */
    @TableId(value = "user_photo_id", type = IdType.AUTO)
    private Long userPhotoId;

    /**
     * 创建者用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 照片ID
     */
    @TableField(value = "photo_id")
    private Long photoId;

    /**
     * 封面图片URL
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 照片集描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 上传时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除，0表示未删除，1表示已删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}