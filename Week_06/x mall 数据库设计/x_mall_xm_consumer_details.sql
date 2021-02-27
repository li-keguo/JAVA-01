create table xm_consumer_details
(
    consumer_details_id bigint auto_increment comment '消费者详情id'
        primary key,
    consumer_id         bigint                             not null comment '消费者id',
    email               varchar(200)                       null comment '邮件地址',
    id_card             varchar(50)                        null comment '省份证号码',
    birthday            date                               null comment '生日',
    sex                 int      default 0                 not null comment '性别：0未知 1男 2女',
    consumer_integral   int      default 0                 not null comment '用户积分',
    address             varchar(200)                       null comment '用户地址',
    consumer_preference varchar(200)                       null comment '用户偏好',
    create_time         datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time         datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint xm_consumer_details_pk_consumer_id
        unique (consumer_id),
    constraint xm_consumer_details_pk_id_card
        unique (id_card)
)
    comment '消费者-用户-详情';

INSERT INTO `x-mall`.xm_consumer_details (consumer_details_id, consumer_id, email, id_card, birthday, sex, consumer_integral, address, consumer_preference, create_time, update_time) VALUES (1, 1, '', null, null, 0, 0, null, null, '2021-02-27 22:49:37', '2021-02-27 22:49:37');