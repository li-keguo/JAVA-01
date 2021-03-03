package cn.leaf.exercise.repository.mapper;

import cn.leaf.exercise.model.po.XmShoppingOrder;
import cn.leaf.exercise.support.NewMySqlMapper;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李克国
 * @date 2021/3/2 9:13 下午
 */

@Mapper
public interface XmShoppingOrderMapper extends NewMySqlMapper<XmShoppingOrder> {

    @Flush
    void flush();
}
