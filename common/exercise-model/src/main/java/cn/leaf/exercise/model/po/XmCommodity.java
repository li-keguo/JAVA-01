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
@Table(name = "xm_commodity")
public class XmCommodity implements Serializable {

    private static final long serialVersionUID = 1615865576490L;


    /**
     * 主键
     * 产品id
     * isNullAble:0
     */
    @Id
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
     * 商品名称
     * isNullAble:0
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 建议单价
     * isNullAble:0,defaultVal:0.00000000
     */
    @Column(name = "commodity_quantity")
    private java.math.BigDecimal commodityQuantity;

    /**
     * 数量单位
     * isNullAble:0
     */
    @Column(name = "quantity_unit")
    private String quantityUnit;

    /**
     * 商品单价
     * isNullAble:0,defaultVal:0.00000000
     */
    @Column(name = "commodity_unit_price")
    private java.math.BigDecimal commodityUnitPrice;

    /**
     * 折扣策略（此处简化，直接写折扣力度）
     * isNullAble:0,defaultVal:100.00000000
     */
    @Column(name = "discount_strategy")
    private java.math.BigDecimal discountStrategy;

    /**
     * 商品简介：默认为产品快照
     * isNullAble:0
     */
    @Column(name = "commodity_introduction")
    private String commodityIntroduction;

    /**
     * 修改人(记录修改人真实姓名,管理员维护时跟新)
     * isNullAble:0
     */
    @Column(name = "update_user")
    private String updateUser;

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
