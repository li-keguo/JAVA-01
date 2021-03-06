package cn.leaf.exercise.shardingsphere.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 李克国
 * @date 2021/3/2 9:10 下午
 */
@Data
@Builder
@AllArgsConstructor
@Table(name = "xm_shopping_order")
public class XmShoppingOrder {
    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;
    /**
     * 用户id
     */
    @Column(name = "consumer_id")
    private Integer consumerId;
    /**
     * 送货地址id
     */
    @Column(name = "shipping_address_id")
    private Integer shippingAddressId;
    /**
     * 消费者电话号码
     */
    @Column(name = "consumer_phone")
    private String consumerPhone;
    /**
     * 送货地址快照
     */
    @Column(name = "address_snapshot")
    private String addressSnapshot;
    /**
     * 订单状态
     */
    @Column(name = "order_status")
    @Builder.Default
    private String orderStatus = "未支付";
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Builder.Default
    private Date createTime = new Date();
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @Builder.Default
    private Date updateTime = new Date();

    public XmShoppingOrder() {
    }
}
