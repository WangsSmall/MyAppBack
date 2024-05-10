package com.wzh.myapp.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户照片表
 * @TableName photo
 */
@TableName(value ="photo")
@Data
public class Photo implements Serializable {
    /**
     * 照片ID
     */
    @TableId(value = "photo_id", type = IdType.AUTO)
    private Long photoId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 作者
     */
    @TableField(value = "photo_author")
    private String photoAuthor;

    /**
     * 照片URL地址
     */
    @TableField(value = "photo_url")
    private String photoUrl;

    /**
     * 照片封面
     */
    @TableField(value = "cover")
    private String cover;

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