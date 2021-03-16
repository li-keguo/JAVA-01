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
@Table(name = "xm_product_details")
public class XmProductDetails implements Serializable {

    private static final long serialVersionUID = 1615865597607L;


    /**
     * 主键
     * 产品id
     * isNullAble:0
     */
    @Id
    @Column(name = "update_details_id")
    private Long productDetailsId;

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
     * 详细描述
     * isNullAble:1
     */
    @Column(name = "product_details")
    private String productDetails;

    /**
     * 修改人(记录修改人真实姓名)
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
