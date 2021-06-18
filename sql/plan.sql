
use ims;

-- 客户问卷表
drop table if exists `ismp_plan_understand_customer`;
create table `ismp_plan_understand_customer` (
    `id` bigint(6) not null auto_increment comment 'id',
    `user_id` bigint(6) not null commment '客服id',
    `customer_name` varchar(100) null comment '用户姓名',
    `fund_account` varchar(100) null comment '资金账号',
    `survey_name` varchar(100) null comment '问卷名称',
    `customer_type` int(4) null comment '客户类别',
    `purpose` int(4) null comment '客户目标',
    `score` decimal(10, 16) comment '得分',
    `status` int(4) not null comment '状态',


    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '修改时间',
    primary key (`id`),
    key `index_user_id`(`user_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;


-- 问卷-计划关联表
drop table if exists `ismp_plan_formulate_customer_relation`;
create table `ismp_plan_formulate_customer_relation` (
    `id` bigint(6) not null auto_increment comment 'id',
    `uc_id` bigint(6) not null comment 'ismp_plan_understand_customer表id',
    `f_id` bigint(6) not null comment 'ismp_plan_formulate表id',
    primary key (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;



-- 计划主表
drop table if exists `ismp_plan_formulate`;
create table `ismp_plan_formulate` (
    `id` bigint(6) not null auto_increment comment 'id',
    `user_id` bigint(6) not null commment '客服id',
    `customer_name` varchar(100) null comment '用户姓名',
    `fund_account` varchar(100) null comment '资金账号',
    `plan_name` varchar(100) null comment '计划名称',
    `customer_type` int(4) null comment '客户类别',
    `purpose` int(4) null comment '客户目标',
    `score` decimal(10, 16) comment '得分',
    `status` int(4) not null comment '状态',
    `customer_open_last` datetime comment '客户最新查看时间',


    `create_time` datetime comment '创建时间',
    `update_time` datetime comment '修改时间',
    primary key (`id`),
    key `index_user_id`(`user_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;


-- 客户操作记录
drop table if exist `ismp_plan_operate_log`;
create table `ismp_plan_operate_log` (
    `id` bigint(6) not null auto_increment comment 'id',
    `f_id` bigint(6) not null comment '计划表id',
    `operate_time` datetime comment '操作时间',
    `operate_type` int(6) comment '操作类型',
    `channel` int(6) comment '渠道',
    primary key (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;

-- 功能权限
drop table if exists `ismp_authority_resource`;
create table `ismp_authority_resource` (
    `id` bigint(6) not null auto_increment comment 'id',
    `resource_type` varchar(50) not null comment '资源类型',
    `resource_value` varchar(100) null comment '资源',

    `create_time` datetime comment '创建时间',
    `create_id` bigint(6) not null comment '创建人',
    `update_time` datetime comment '修改时间',
    `update_id` bigint(6) not null comment '修改人',
    primary key (`id`),
    key `index_user_id`(`user_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;

drop table if exists `ismp_authority_group`;
create table `ismp_authority_group` (

)




