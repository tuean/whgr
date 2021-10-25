use whgr;
drop table if exists `todo_info`;
create table `todo_info`(
    `id`                bigint(20)      NOT NULL auto_increment comment 'id',
    `title`             varchar(100)    NOT NULL comment '标题',
    `content`           text            NOT NULL comment '正文',
    `creator`           varchar(100)    NOT NULL comment '创建人',
    `create_time`       datetime        NOT NULL comment '创建时间',
    `update_time`       datetime        NOT NULL comment '修改时间',
    primary key(`id`),
    key `index_title`(`title`),
    key `index_update_time`(`update_time`)
) Engine=InnoDB default charset = utf8mb4 comment '代办事项';