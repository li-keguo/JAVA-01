package cn.leaf.exercise.exercise.po;

import java.util.Date;
import javax.persistence.Table;

/**
 * @author Liutianyou
 * @date 2021/3/2 9:10 下午
 */
@Table(name = "xm_shopping_order")
public class Order {

  String orderId;
  Integer consumerId;
  Integer shippingAddressId;
  String consumerPhone;
  String addressSnapshot;
  String orderStatus;
  Date createTime;
  Date updateTime;

  public Order(String orderId, Integer consumerId,String consumerPhone, Integer shippingAddressId,
       String addressSnapshot, String orderStatus) {
    this.orderId = orderId;
    this.consumerId = consumerId;
    this.shippingAddressId = shippingAddressId;
    this.consumerPhone = consumerPhone;
    this.addressSnapshot = addressSnapshot;
    this.orderStatus = orderStatus;
    this.createTime = new Date();
    this.updateTime = this.createTime;
  }


}
