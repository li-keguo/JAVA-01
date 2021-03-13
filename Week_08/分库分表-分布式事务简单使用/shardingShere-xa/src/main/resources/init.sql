create database xm_db_01;
use xm_db_01;
create table xm_shopping_order_0
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_1
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_2
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_3
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_4
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_5
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_6
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_7
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_8
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_9
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_10
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_11
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_12
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_13
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_14
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_15
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
## 分库二
create database xm_db_02;
use xm_db_02;
create table xm_shopping_order_0
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_1
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_2
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_3
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_4
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_5
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_6
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_7
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_8
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_9
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_10
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_11
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_12
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_13
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_14
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
create table xm_shopping_order_15
(
    order_id            varchar(100)              not null comment '订单id',
    consumer_id         bigint                    not null comment '消费者id',
    consumer_phone      varchar(20)               not null comment '消费者电话',
    shipping_address_id bigint                    not null comment '收货地址',
    address_snapshot    varchar(500)              not null comment '收货地址快照',
    order_status        varchar(10) default '未支付' not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default now() not null comment '创建时间',
    update_time         datetime    default now() not null comment '修改时间',
    constraint xm_shopping_order_pk
        primary key (order_id)
)
    comment '订单';
