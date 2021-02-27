create table xm_consumer
(
    consumer_id              bigint auto_increment comment '消费者id'
        primary key,
    login_name               varchar(200)                       not null comment '登录名称',
    nickname                 varchar(200)                       not null comment '昵称',
    login_password           varchar(50)                        not null comment '密码',
    vip_level                int      default 0                 not null comment 'vip等级',
    phone                    varchar(20)                        not null comment '电话号码',
    default_shopping_address binary(1)                          null comment '默认用户地址',
    create_time              datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time              datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint xm_consumer_pk_login_name
        unique (login_name),
    constraint xm_consumer_pk_phone
        unique (phone)
)
    comment '消费者-用户';

INSERT INTO `x-mall`.xm_consumer (consumer_id, login_name, nickname, login_password, vip_level, phone, default_shopping_address, create_time, update_time) VALUES (1, 'likeguo', 'likeguo', '123', 0, '18188880709', null, '2021-02-27 22:48:07', '2021-02-27 22:48:07');