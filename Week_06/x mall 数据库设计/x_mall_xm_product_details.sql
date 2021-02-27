create table xm_product_details
(
    product_details_id bigint auto_increment comment '产品id'
        primary key,
    product_id         bigint                             not null comment '产品id',
    product_code       varchar(100)                       not null comment '商品编码（可做主键，用户录入或生成）',
    product_name       varchar(200)                       not null comment '商品名称',
    product_details    text                               null comment '详细描述',
    update_user        varchar(100)                       not null comment '修改人(记录修改人真实姓名)',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint xm_product_details_pk_product_code
        unique (product_code),
    constraint xm_product_details_pk_product_id
        unique (product_id)
)
    comment '产品-详情';

INSERT INTO `x-mall`.xm_product_details (product_details_id, product_id, product_code, product_name, product_details, update_user, create_time, update_time) VALUES (1, 1, 'PSGCC10010001', '可口可乐', '非常好喝，肥宅快乐水', 'admin', '2021-02-27 23:10:25', '2021-02-27 23:10:25');