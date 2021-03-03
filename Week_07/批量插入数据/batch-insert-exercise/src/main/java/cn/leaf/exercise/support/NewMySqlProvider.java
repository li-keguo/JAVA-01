package cn.leaf.exercise.support;

import cn.hutool.core.collection.ListUtil;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.List;
import java.util.Set;

/**
 * 自定义新的MySQL通用mapper提供者
 *
 * @author 李克国
 * @version 1.0.0
 * @description 自定义新的MySQL通用mapper提供者
 */
public class NewMySqlProvider extends MapperTemplate {
    public NewMySqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String saves(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = getInsertHandStringBuilder(entityClass);
        buildValues(columnList, sql);
        onDuplicateKeyUpdate(columnList, sql);
        return sql.toString();
    }

    public String inserts(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(getEntityClass(ms));
        StringBuilder sql = getInsertHandStringBuilder(entityClass);
        buildValues(columnList, sql);
        return sql.toString();
    }

    public String save(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = getInsertHandStringBuilder(entityClass);
        sql.append(" VALUES ");
        values(columnList, sql);
        onDuplicateKeyUpdate(columnList, sql);
        return sql.toString();
    }

    private StringBuilder getInsertHandStringBuilder(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        return sql;
    }

    private void buildValues(Set<EntityColumn> columnList, StringBuilder sql) {
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        values(columnList, sql);
        sql.append("</foreach>");
    }

    private void values(Set<EntityColumn> columnList, StringBuilder sql) {
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            sql.append(column.getColumnHolder("record", null, ","));
        }
        sql.append("</trim>");
    }

    private void onDuplicateKeyUpdate(Set<EntityColumn> columnList, StringBuilder sql) {
        sql.append("ON DUPLICATE KEY UPDATE ");
        List<EntityColumn> list = ListUtil.list(false, columnList);
        for (int i = 0; i < list.size(); i++) {
            // 忽略主键修改
            if (list.get(i).isId()) {
                continue;
            }
            sql.append(list.get(i).getColumn())
                    .append("= VALUES(")
                    .append(list.get(i).getColumn())
                    .append(i == list.size() - 1 ? ")" : "),");

        }
    }
}
