create table xm_shopping_order_detail
(
    order_details_id   bigint auto_increment comment '订单明细id'
        primary key,
    order_id           varchar(100)                             not null comment '订单id',
    consumer_id        bigint                                   not null comment '消费者id',
    commodity_id       bigint                                   not null comment '商品id',
    product_id         bigint                                   not null comment '产品id',
    product_code       varchar(100)                             not null comment '商品编码（可做主键，用户录入或生成）',
    commodity_quantity decimal(20, 8) default 0.00000000        not null comment '商品数量',
    commodity_price    decimal(20, 8) default 0.00000000        not null comment '商品单价：打折后',
    detail_snapshot    varchar(500)                             not null comment '详情快照',
    create_time        datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime       default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '订单明细';

INSERT INTO `x-mall`.xm_shopping_order_detail (order_details_id, order_id, consumer_id, commodity_id, product_id, product_code, commodity_quantity, commodity_price, detail_snapshot, create_time, update_time) VALUES (1, '1', 1, 1, 1, 'PSGCC10010001', 5.00000000, 15.72000000, '可口可乐5瓶装，9折优惠只需15.72元', '2021-02-28 01:06:26', '2021-02-28 01:06:26');