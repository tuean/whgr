SET NAMES utf8mb4;

create database if not exists whgr;

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
    `role` int(6) not null default 0 comment '可见角色：0 所有人，N 表示 role<=N 的账号可见',
    primary key(`id`)
) Engine=InnoDB auto_increment=10000 default charset=utf8mb4 comment '菜单表';

-- 初始数据：管理员 admin / admin（role=1），普通用户 normal / normal（role=2，看不到账号管理）
insert into `admin_account` (account, name, pwd, mobile, role, status, create_time, creater, modify_time, modifier)
values ('admin', '管理员', md5('admin'), '', 1, 1, now(), 'system', now(), 'system'),
       ('normal', '普通用户', md5('normal'), '', 2, 1, now(), 'system', now(), 'system');

-- 初始数据：侧边菜单（menu_url 对应前端路由）
insert into `menus` (menu_name, menu_type, menu_url, role) values
('AI 入口', 1, '/ai', 0),
('工作台', 1, '/dashboard', 0),
('账号管理', 1, '/account', 1);

-- AI 入口的问答与决策轨迹（回溯用）
create table `ai_log` (
    `id` bigint(20) unsigned auto_increment comment '数据id',
    `admin_id` bigint(20) not null comment '发起人',
    `message` varchar(500) not null comment '用户输入',
    `reply` text comment '模型最终回复',
    `actions` text comment '跳转动作 JSON',
    `trace` mediumtext comment '思考/决策轨迹 JSON',
    `create_time` datetime not null comment '时间',
    primary key(`id`),
    key `admin_id_index`(`admin_id`)
) Engine=InnoDB auto_increment=10000 default charset=utf8mb4 comment 'AI 入口问答与轨迹表';
