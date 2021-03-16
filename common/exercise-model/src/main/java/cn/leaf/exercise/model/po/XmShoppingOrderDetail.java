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
@Table(name = "xm_shopping_order_detail")
public class XmShoppingOrderDetail implements Serializable {

    private static final long serialVersionUID = 1615865613541L;


    /**
     * 主键
     * 订单明细id
     * isNullAble:0
     */
    @Id
    @Column(name = "order_details__id")
    private Long orderDetailsId;

    /**
     * 订单id
     * isNullAble:0
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 消费者id
     * isNullAble:0
     */
    @Column(name = "consumer_id")
    private Long consumerId;

    /**
     * 商品id
     * isNullAble:0
     */
    @Column(name = "commodity_id")
    private Long commodityId;

    /**
     * 产品id
     * isNullAble:0
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 商品编码（可做主键，用户录入或生成）
     * isNullAble:0
     */
    @Column(name = "product_code")
    private String productCode;

    /**
     * 商品数量
     * isNullAble:0,defaultVal:0.00000000
     */
    @Column(name = "commodity_quantity")
    private java.math.BigDecimal commodityQuantity;

    /**
     * 商品单价：打折后
     * isNullAble:0,defaultVal:0.00000000
     */
    @Column(name = "commodity_price")
    private java.math.BigDecimal commodityPrice;

    /**
     * 详情快照
     * isNullAble:0
     */
    @Column(name = "detail_snapshot")
    private String detailSnapshot;

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
