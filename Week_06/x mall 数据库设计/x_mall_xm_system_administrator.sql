create table xm_system_administrator
(
    administrator_id int auto_increment comment '管理员id'
        primary key,
    login_name       varchar(200)                          not null comment '登录名称',
    login_password   varchar(50)                           not null comment '密码',
    user_role        varchar(20) default '超级管理员'           not null comment '角色（决定权限，此处简单字符串表示）',
    phone            varchar(20)                           not null comment '电话号码',
    id_card          varchar(20)                           not null comment '省份证号码（管理员必须实名）',
    real_name        varchar(20)                           not null comment '真实姓名（管理员必须实名）',
    create_time      datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime    default CURRENT_TIMESTAMP not null comment '修改时间',
    constraint xm_system_administrator_pk_login_name
        unique (login_name),
    constraint xm_system_administrator_pk_phone
        unique (phone)
)
    comment '系统管理员';

INSERT INTO `x-mall`.xm_system_administrator (administrator_id, login_name, login_password, user_role, phone, id_card, real_name, create_time, update_time) VALUES (1, 'admin', '123', '', '18188880709', '622628202101015212', 'admin', '2021-02-27 22:46:50', '2021-02-27 22:46:50');