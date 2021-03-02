package cn.leaf.exercise.exercise;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通用Mapper扩展
 *
 * @author 李克国
 * @version 1.0.0
 * @project px-settlement-tieline-tradedata
 * @Date 2020/9/25 9:24
 * @description 通用Mapper扩展
 * @see NewMySqlProvider
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface NewMySqlMapper<T> {

    /**
     * 保存：提供一个列表，持久化到数据库
     * <p> 如果不存在，则新增，如果存在，怎更新</p>
     * <p> 保证表结构设计，必须包含主键</p>
     *
     * @param recordList 保存数据
     * @return update size
     */
    @Options(useGeneratedKeys = true)
    @InsertProvider(type = NewMySqlProvider.class, method = "dynamicSQL")
    int saves(List<? extends T> recordList);

    /**
     * 批量插入
     *
     * @param recordList 保存数据
     * @return update size
     */
    @Options(useGeneratedKeys = true)
    @InsertProvider(type = NewMySqlProvider.class, method = "dynamicSQL")
    int inserts(List<? extends T> recordList);

    /**
     * 保存：单个实体
     * <p> 如果不存在，则新增，如果存在，则更新</p>
     * <p> 保证表结构设计，必须包含主键</p>
     *
     * @param record 保存数据
     * @return update size
     */
    @Options(useGeneratedKeys = true)
    @InsertProvider(type = NewMySqlProvider.class, method = "dynamicSQL")
    int save(@Param("record") T record);
}
