create table xm_shipping_address
(
    address_id      bigint auto_increment comment '地址id'
        primary key,
    consumer_id     bigint                             not null comment '消费者id',
    province        varchar(20)                        not null comment '省',
    city            varchar(30)                        not null comment '市',
    county          varchar(30)                        not null comment '县区',
    address_details varchar(500)                       not null comment '详细地址',
    full_address    varchar(800)                       not null comment '全地址',
    consignee_phone varchar(20)                        not null comment '收货人电话',
    consignee_name  varchar(20)                        not null comment '收货人姓名',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null comment '修改时间'
)
    comment '收货地址';

INSERT INTO `x-mall`.xm_shipping_address (address_id, consumer_id, province, city, county, address_details, full_address, consignee_phone, consignee_name, create_time, update_time) VALUES (1, 1, '北京', '北京', '昌平', '昌平XXXXXX', '北京市昌平XXXXXX', '18188880709', '李克国', '2021-02-28 00:32:36', '2021-02-28 00:32:36');