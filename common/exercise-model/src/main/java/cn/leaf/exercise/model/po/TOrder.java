package cn.leaf.exercise.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 李克国
 * @date 2021/3/2 9:10 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_order")
public class TOrder {

    /**
     * 送货地址id
     */
    @javax.persistence.Id
    private Integer id;
    /**
     * 订单id
     */
    @Column(name = "order_no")
    private String orderNo;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;


}
