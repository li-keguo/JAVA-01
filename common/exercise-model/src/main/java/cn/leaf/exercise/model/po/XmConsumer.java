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
 * @author author
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "xm_consumer")
public class XmConsumer implements Serializable {

    private static final long serialVersionUID = 1615865584671L;


    /**
     * 主键
     * 消费者id
     * isNullAble:0
     */
    @Id
    @Column(name = "consumer_id")
    private Long consumerId;

    /**
     * 登录名称
     * isNullAble:0
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 昵称
     * isNullAble:0
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 密码
     * isNullAble:0
     */
    @Column(name = "login_password")
    private String loginPassword;

    /**
     * vip等级
     * isNullAble:0,defaultVal:0
     */
    @Column(name = "vip_level")
    private Integer vipLevel;

    /**
     * 电话号码
     * isNullAble:0
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 默认用户地址
     * isNullAble:1
     */
    @Column(name = "default_shopping_address")
    private byte[] defaultShoppingAddress;

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
