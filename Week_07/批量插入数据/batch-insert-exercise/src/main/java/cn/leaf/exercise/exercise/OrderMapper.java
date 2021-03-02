package cn.leaf.exercise.exercise;

import cn.leaf.exercise.exercise.po.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Liutianyou
 * @date 2021/3/2 9:13 下午
 */

@Mapper
public interface OrderMapper extends NewMySqlMapper<Order> {

}
