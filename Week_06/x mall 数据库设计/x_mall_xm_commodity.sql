create table xm_commodity
(
    commodity_id           bigint auto_increment comment '产品id'
        primary key,
    product_id             bigint                                   not null comment '产品id',
    product_code           varchar(100)                             not null comment '商品编码（可做主键，用户录入或生成）',
    product_name           varchar(200)                             not null comment '商品名称',
    commodity_quantity     decimal(20, 8) default 0.00000000        not null comment '建议单价',
    quantity_unit          varchar(20)                              not null comment '数量单位',
    commodity_unit_price   decimal(20, 8) default 0.00000000        not null comment '商品单价',
    discount_strategy      decimal(20, 8) default 100.00000000      not null comment '折扣策略（此处简化，直接写折扣力度）',
    commodity_introduction varchar(3000)                            not null comment '商品简介：默认为产品快照',
    update_user            varchar(100)                             not null comment '修改人(记录修改人真实姓名,管理员维护时跟新)',
    create_time            datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time            datetime       default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint xm_commodity_pk_product_code
        unique (product_code),
    constraint xm_commodity_pk_product_id
        unique (product_id)
)
    comment '商品';

INSERT INTO `x-mall`.xm_commodity (commodity_id, product_id, product_code, product_name, commodity_quantity, quantity_unit, commodity_unit_price, discount_strategy, commodity_introduction, update_user, create_time, update_time) VALUES (1, 1, 'PSGCC10010001', '可口可乐', 5000.00000000, '瓶', 3.50000000, 90.00000000, '大瓶肥宅快乐水9折优惠了，开盖有惊喜，再来一瓶', 'admin', '2021-02-28 00:22:21', '2021-02-28 00:22:21');