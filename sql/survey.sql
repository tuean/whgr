
-- 问卷信息表
create table `ismp_survey` (
    `id` bigint(6) not null auto_increment comment 'id',
    `survey_id` varchar(50) not null comment '问卷编码',
    `survey_name` varchar(100) not null comment '问卷名称',
    `survey_des` varchar(300) null comment '问卷描述',
    `logo` varchar(1000) null comment 'logo地址',

    `survey_version` int(6) not null default 0 comment '版本号',
    `create_time` datetime default null comment '创建时间',
    `update_time` datetime default null comment '修改时间',
    `creator` varchar(100) null comment '创建人',
    `updater` varchar(100) null comment '修改人',
    primary key(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;

-- 题目表
create table `ismp_survey_question` (
    `id` bigint(6) not null auto_increment comment 'id',
    `survey_id` varchar(50) not null comment '问卷编码',
    `survey_version` int(6) not null default 0 comment '版本号',
    `question_name` varchar(100) not null comment '题目名称',
    `question_type` int(5) not null comment '题目类型',
    `required` int(2) not null comment '是否必填',
    `index` int(4) not null comment '题目顺序',
    `link` bigint(6) null comment '跳转id',
    `has_text` int(2) not null comment '是否允许自定义输入',

    `question_version` int(6) not null default 0 comment '版本号',
    `create_time` datetime default null comment '创建时间',
    `update_time` datetime default null comment '修改时间',
    `creator` varchar(100) null comment '创建人',
    `updater` varchar(100) null comment '修改人',
    primary key(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;

-- 答案表
create table `ismp_survey_answer` (
    `id` bigint(6) not null auto_increment comment 'id',
    `question_id` bigint(6) not null comment '题目id',
    `question_version` int(6) not null default 0 comment '版本号',
    `index` int(4) not null comment '答案顺序',
    `answer_value` varchar(20) null comment '答案序号',
    `answer_text` varchar(1000) null comment '显示字段',
    `link` bigint(6) null comment '跳转id',

    `answer_version` int(6) not null default 0 comment '版本号',
    `create_time` datetime default null comment '创建时间',
    `update_time` datetime default null comment '修改时间',
    `creator` varchar(100) null comment '创建人',
    `updater` varchar(100) null comment '修改人',
    primary key(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;

-- 回答记录表
create table `ismp_survey_record` (
    `id` bigint(6) not null auto_increment comment 'id',
    `user_id` varchar(100) not null comment '用户唯一标识',
    `user_type` int(4) not null comment '用户标识类型',
    `question_id` bigint(6) not null comment '题目id',
    `question_version` int(6) not null default 0 comment '版本号',
    `answer_id` bigint(6) not null comment '答案id',
    `answer_version` int(6) not null default 0 comment '版本号',

    `answer_value` varchar(20) null comment '答案序号',
    `default_selected` int(2) not null comment '是否默认选中',

    `create_time` datetime default null comment '创建时间',
    `update_time` datetime default null comment '修改时间',
    primary key(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;

-- 意见表
create table `ismp_survey_opinion` (
   `id` bigint(6) not null auto_increment comment 'id',
   `user_id` varchar(100) not null comment '用户唯一标识',
   `user_type` int(4) not null comment '用户标识类型',
   `question_id` bigint(6) not null comment '题目id',
   `question_version` int(6) not null default 0 comment '版本号',
   `answer_id` bigint(6) not null comment '答案id',
   `answer_version` int(6) not null default 0 comment '版本号',

   `opinion` varchar(1000) not null comment '回答意见',

   `create_time` datetime default null comment '创建时间',
   `update_time` datetime default null comment '修改时间',
   primary key(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET = utf8mb4;