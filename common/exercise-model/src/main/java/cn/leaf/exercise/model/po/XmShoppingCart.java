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
@Table(name = "xm_shopping_order")
public class XmShoppingCart implements Serializable {

    private static final long serialVersionUID = 1615865604809L;


    /**
     * 主键
     * 购物车id
     * isNullAble:0
     */
    @Id
    @Column(name = "cart_id")
    private Long cartId;

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
     * 建议单价
     * isNullAble:0,defaultVal:0.00000000
     */
    @Column(name = "commodity_quantity")
    private java.math.BigDecimal commodityQuantity;

    /**
     * 商品单价
     * isNullAble:0,defaultVal:0.00000000
     */
    @Column(name = "commodity_price")
    private java.math.BigDecimal commodityPrice;

    /**
     * 详情快照
     * isNullAble:0
     */
    @Column(name = "cart_snapshot")
    private String cartSnapshot;

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
