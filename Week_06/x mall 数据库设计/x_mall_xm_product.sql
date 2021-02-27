create table xm_product
(
    product_id           bigint auto_increment comment '产品id'
        primary key,
    product_code         varchar(100)                             not null comment '商品编码（可做主键，用户录入或生成）',
    product_name         varchar(200)                             null comment '商品名称',
    product_snapshot     varchar(3000)                            null comment '产品快照（简短信息）',
    product_type         varchar(100)                             null comment '产品类型',
    suggested_unit_price decimal(20, 8) default 0.00000000        not null comment '建议单价',
    update_user          varchar(100)                             not null comment '修改人(记录修改人真实姓名)',
    create_time          datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time          datetime       default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint xm_product_pk_product_code
        unique (product_code)
)
    comment '产品';

INSERT INTO `x-mall`.xm_product (product_id, product_code, product_name, product_snapshot, product_type, suggested_unit_price, update_user, create_time, update_time) VALUES (1, 'PSGCC10010001', '可口可乐', '可口可乐-肥宅快乐水', '饮料', 3.00000000, 'admin', '2021-02-27 23:02:01', '2021-02-27 23:02:01');