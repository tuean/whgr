create database whgr;

use whgr;


-- 管理员账号表
create table `admin_account` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `account` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
    `name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
    `pwd` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `mobile` varchar(100) null comment '手机号',
    `open_id` varchar(100) null comment '客户openid',
    `role` int NOT NULL COMMENT '角色',
    `status` int NOT NULL COMMENT '状态',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `creater` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人账号',
    `modify_time` datetime NOT NULL COMMENT '修改时间',
    `modifier` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '修改人账号',
    primary key (`id`),
    key `account_index`(`account`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账号表';


-- 管理员登录信息表
create table `admin_token` (
    `id` bigint(20) unsigned auto_increment comment '数据id',
    `admin_id` bigint(20) unsigned null comment '客户id',
    `open_id` varchar(100) null comment '客户openid',
    `union_id` varchar(100) null comment '客户unionid',
    `session_key` varchar(200) not null comment 'session_key',

    `token` varchar(100) not null comment '登录token',
    `token_type` int(5) not null comment 'token类型 pc端 wx端',

    `create_time` datetime not null comment '创建时间',
    `modify_time` datetime not null comment '修改时间',
    `status` int(5) not null comment '记录状态',

    primary key(`id`),
    key `admin_id_index`(`admin_id`),
    key `open_id_index`(`open_id`),
    key `union_id_index`(`union_id`),
    key `token_index`(`token`)
) Engine=InnoDB auto_increment=10000 default charset=utf8mb4 comment '管理员登录信息表';


-- 菜单表
create table `menus` (
    `id` bigint(20) unsigned auto_increment comment '数据id',
    `menu_name` varchar(100) not null comment '菜单名称',
    `menu_type` int(6) not null comment '菜单类型',
    `menu_url` varchar(100) not null comment '菜单地址',
    primary key(`id`)
) Engine=InnoDB auto_increment=10000 default charset=utf8mb4 comment '菜单表';