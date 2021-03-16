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
@Table(name = "xm_shopping_address")
public class XmShippingAddress implements Serializable {

    private static final long serialVersionUID = 1615865601163L;


    /**
     * 主键
     * 地址id
     * isNullAble:0
     */
    @Id
    @Column(name = "address_id")
    private Long addressId;

    /**
     * 消费者id
     * isNullAble:0
     */
    @Column(name = "consumer_id")
    private Long consumerId;

    /**
     * 省
     * isNullAble:0
     */
    @Column(name = "province")
    private String province;

    /**
     * 市
     * isNullAble:0
     */
    @Column(name = "city")
    private String city;

    /**
     * 县区
     * isNullAble:0
     */
    @Column(name = "county")
    private String county;

    /**
     * 详细地址
     * isNullAble:0
     */
    @Column(name = "address_details")
    private String addressDetails;

    /**
     * 全地址
     * isNullAble:0
     */
    @Column(name = "full_address")
    private String fullAddress;

    /**
     * 收货人电话
     * isNullAble:0
     */
    @Column(name = "consignee_phone")
    private String consigneePhone;

    /**
     * 收货人姓名
     * isNullAble:0
     */
    @Column(name = "consignee_name")
    private String consigneeName;

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
