package cn.leaf.exercise.repository.mapper;

import cn.leaf.exercise.model.po.TOrder;
import cn.leaf.exercise.model.po.XmShoppingOrder;
import org.apache.ibatis.annotations.Flush;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 李克国
 * @date 2021/3/2 9:13 下午
 */
@org.apache.ibatis.annotations.Mapper
public interface TOrderMapper extends Mapper<TOrder> {

    @Flush
    void flush();
}
