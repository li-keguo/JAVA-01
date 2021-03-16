package cn.leaf.exercise.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author likeguo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "xm_system_administrator")
public class XmSystemAdministrator implements Serializable {

    private static final long serialVersionUID = 1615865620401L;


    /**
     * 主键
     * 管理员id
     * isNullAble:0
     */
    @Id
    @Column(name = "administrator_id")
    private Integer administratorId;

    /**
     * 登录名称
     * isNullAble:0
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 密码
     * isNullAble:0
     */
    @Column(name = "login_password")
    private String loginPassword;

    /**
     * 角色（决定权限，此处简单字符串表示）
     * isNullAble:0,defaultVal:超级管理员
     */
    @Column(name = "user_role")
    private String userRole;

    /**
     * 电话号码
     * isNullAble:0
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 省份证号码（管理员必须实名）
     * isNullAble:0
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 真实姓名（管理员必须实名）
     * isNullAble:0
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 创建时间
     * isNullAble:0,defaultVal:CURRENT_TIMESTAMP
     */
    @Column(name = "create_time")
    private java.time.LocalDateTime createTime;

    /**
     * 修改时间
     * isNullAble:0,defaultVal:CURRENT_TIMESTAMP
     */
    @Column(name = "update_time")
    private java.time.LocalDateTime updateTime;


}
