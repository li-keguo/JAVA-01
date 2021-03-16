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
@Table(name = "xm_consumer_details")
public class XmConsumerDetails implements Serializable {

    private static final long serialVersionUID = 1615865587844L;


    /**
     * 主键
     * 消费者详情id
     * isNullAble:0
     */
    @Id
    @Column(name = "consumer_details_id")
    private Long consumerDetailsId;

    /**
     * 消费者id
     * isNullAble:0
     */
    @Column(name = "consumer_id")
    private Long consumerId;

    /**
     * 邮件地址
     * isNullAble:1
     */
    @Column(name = "email")
    private String email;

    /**
     * 省份证号码
     * isNullAble:1
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 生日
     * isNullAble:1
     */
    @Column(name = "birthday")
    private java.time.LocalDate birthday;

    /**
     * 性别：0未知 1男 2女
     * isNullAble:0,defaultVal:0
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 用户积分
     * isNullAble:0,defaultVal:0
     */
    @Column(name = "consumer_integral")
    private Integer consumerIntegral;

    /**
     * 用户地址
     * isNullAble:1
     */
    @Column(name = "address")
    private String address;

    /**
     * 用户偏好
     * isNullAble:1
     */
    @Column(name = "consumer_preference")
    private String consumerPreference;

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
