-- auto-generated definition
CREATE DATABASE myapp CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

use myapp;

CREATE TABLE `user`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`      varchar(50)                      DEFAULT NULL COMMENT '用户昵称',
    `user_account`  varchar(50)                      DEFAULT NULL COMMENT '账号',
    `avatar_url`    varchar(255)                     DEFAULT NULL COMMENT '用户头像',
    `gender`        enum ('male', 'female', 'other') DEFAULT NULL COMMENT '性别',
    `user_password` varchar(255) NOT NULL COMMENT '密码',
    `phone`         varchar(20)                      DEFAULT NULL COMMENT '电话',
    `email`         varchar(255)                     DEFAULT NULL COMMENT '邮箱',
    `user_status`   tinyint(1)   NOT NULL            DEFAULT '0' COMMENT '状态 0-正常',
    `create_time`   timestamp    NOT NULL            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp    NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    tinyint(1)   NOT NULL            DEFAULT '0' COMMENT '是否删除',
    `user_role`     tinyint(1)   NOT NULL            DEFAULT '0' COMMENT '用户角色 0-普通用户 1-管理员',
    `my_code`       varchar(50)                      DEFAULT NULL COMMENT '自己编号',
    `tags`          varchar(255)                     DEFAULT NULL COMMENT '标签列表 Varchar',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_user_account` (`user_account`),
    INDEX `idx_user_role` (`user_role`),
    INDEX `idx_user_status` (`user_status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
    COMMENT ='用户信息表';

CREATE TABLE `article`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT '文章ID',
    `title`        varchar(255) NOT NULL COMMENT '文章标题',
    `content`      text         NOT NULL COMMENT '文章内容',
    `author`       varchar(50)  NOT NULL COMMENT '作者',
    `publish_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `update_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `cover`        varchar(255)          DEFAULT NULL COMMENT '文章封面',
    `like_count`   int(11)      NOT NULL DEFAULT 0 COMMENT '点赞数',
    `is_deleted`   tinyint(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除，0表示未删除，1表示已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章表';
