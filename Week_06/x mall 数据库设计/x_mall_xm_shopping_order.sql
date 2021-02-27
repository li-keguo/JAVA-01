create table xm_shopping_order
(
    order_id            varchar(100)                          not null comment '订单id'
        primary key,
    consumer_id         bigint                                not null comment '消费者id',
    consumer_phone      varchar(20)                           not null comment '消费者电话',
    shipping_address_id bigint                                not null comment '收货地址',
    address_snapshot    varchar(500)                          not null comment '收货地址快照',
    order_status        varchar(10) default '未支付'             not null comment '订单状态：未支付，已支付，取消，完成，未发货，已发货等',
    create_time         datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time         datetime    default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '订单';

INSERT INTO `x-mall`.xm_shopping_order (order_id, consumer_id, consumer_phone, shipping_address_id, address_snapshot, order_status, create_time, update_time) VALUES ('1', 1, '18188880709', 1, '可口可乐X5', '未支付', '2021-02-28 01:08:24', '2021-02-28 01:08:24');