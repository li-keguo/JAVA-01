package cn.leaf.exercise.jdbc.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.leaf.exercise.jdbc.common.JdbcSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单的jdbc curd 的操作支撑
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/16 12:45
 */
@Slf4j
public abstract class SuperDAO<T> {

    private Class<T> clazz;

    private EntityDefinition entityDefinition;


    public SuperDAO() {
        Class<?> typeArgument = ClassUtil.getTypeArgument(this.getClass());
        if (typeArgument != null) {
            clazz = (Class<T>) typeArgument;
        }
        entityDefinition = new EntityDefinition(clazz);
    }

    public List<T> selectAll() {
        String sql = selectAllSql();
        log.debug("select sql : {}", sql);
        return select(sql);
    }

    private String selectAllSql() {
        return "select * from " + entityDefinition.getTableName();
    }


    public List<T> selectByLimit(int limit) {
        String sql = selectAllSql() + "  limit " + limit;
        log.debug("select sql : {}", sql);
        return select(sql);
    }

    public List<T> selectByLimit(int offset, int limit) {
        String sql = selectAllSql() + "  limit " + offset + ", " + limit;
        log.debug("select sql : {}", sql);
        return select(sql);
    }

    protected List<T> select(String sql) {
        List<T> result = new ArrayList<>(16);
        try (Connection connection = getJdbcSession().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery();
        ) {
            while (rs.next()) {
                result.add(create(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer update(T updateBody) {
        StringBuilder sql = new StringBuilder("update ")
                .append(entityDefinition.getTableName());
        List<Object> args = new ArrayList<>();
        Field[] fields = entityDefinition.getFields();
        Field primaryKey = null;
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                primaryKey = field;
            } else {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }
                if (StrUtil.endWith(sql, entityDefinition.getTableName())) {
                    sql.append(" set ");
                }
                sql.append(column.name()).append("=").append("?, ");
                args.add(ReflectUtil.getFieldValue(updateBody, field));
            }
        }
        if (primaryKey == null) {
            throw new IllegalArgumentException("未定义主键id，不支持的update 操作");
        }
        sql = new StringBuilder(sql.subSequence(0, sql.length() - 2));
        Column column = primaryKey.getAnnotation(Column.class);

        sql.append(" where ")
                .append(column == null ? primaryKey.getName() : column.name())
                .append("=").append("? ");
        args.add(ReflectUtil.getFieldValue(updateBody, primaryKey));

        return execute(sql.toString(), args.toArray());
    }

    public void insert(T insertBody) {
        StringBuilder sql = new StringBuilder("insert into ")
                .append(entityDefinition.getTableName())
                .append(" (");

        StringBuilder columns = new StringBuilder();
        StringBuilder columnValues = new StringBuilder();
        List<Object> args = new ArrayList<>();
        Field[] fields = entityDefinition.getFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            columns.append(column == null ? field.getName() : column.name()).append(",");
            args.add(ReflectUtil.getFieldValue(insertBody, field));
            columnValues.append("?,");
        }

        columns = new StringBuilder(columns.subSequence(0, columns.length() - 1));
        columnValues = new StringBuilder(columnValues.subSequence(0, columnValues.length() - 1));
        sql.append(columns).append(") values(").append(columnValues).append(") ");
        execute(sql.toString(), args.toArray());
    }

    public void inserts(List<T> insertBodys) {
        StringBuilder sql = new StringBuilder("insert into ")
                .append(entityDefinition.getTableName())
                .append(" (");

        StringBuilder columns = new StringBuilder();
        StringBuilder columnValues = new StringBuilder();
        Field[] fields = entityDefinition.getFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            columns.append(column == null ? field.getName() : column.name()).append(",");
            columnValues.append("?,");
        }

        columns = new StringBuilder(columns.subSequence(0, columns.length() - 1));
        columnValues = new StringBuilder(columnValues.subSequence(0, columnValues.length() - 1));
        sql.append(columns).append(") values(").append(columnValues).append(") ");

        try (Connection connection = getJdbcSession().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < insertBodys.size(); i++) {
                for (int j = 0; j < fields.length; j++) {
                    statement.setObject(j + 1, ReflectUtil.getFieldValue(insertBodys.get(i), fields[j]));
                }

                statement.addBatch();
                // 10个一批
                if (i % 10 == 0) {
                    statement.executeBatch();
                    log.info("method execute execute:" + statement);
                    statement.clearBatch();
                }
            }
            statement.executeBatch();
            log.info("method execute execute:" + statement);
            log.debug("method execute sql:{}", sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 执行SQL语句，返回受影响的行数
     *
     * @param sql  SLQ 语句
     * @param args SQL语句中？处的参数，需按其顺序传入
     * @return 受影响的行数
     */
    protected Integer execute(String sql, Object... args) {

        try (Connection connection = getJdbcSession().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            log.info("method execute execute:" + statement);
            log.debug("method execute sql:{}", sql);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;

    }

    /**
     * 根据结果集构建一个实体类对象
     *
     * @param rs 数据库结构集
     * @return 构建好的实体类对象
     * @throws SQLException 数据构建异常，来自数据库的异常
     */
    protected T create(ResultSet rs) throws SQLException {
        T target = ReflectUtil.newInstance(clazz);
        for (Field field : entityDefinition.getFields()) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation == null) {
                continue;
            }
            String column = annotation.name();
            if (StrUtil.isBlank(column)) {
                throw new RuntimeException("未知的列定义" + target.getClass().getPackage() + target.getClass().getName());
            }
            ReflectUtil.setFieldValue(target, field, rs.getObject(column));
        }
        return target;
    }

    /**
     * 获取 jdbc session
     *
     * @return {@link JdbcSession}
     */
    public abstract JdbcSession getJdbcSession();

    @Data
    class EntityDefinition {

        private Class<T> clazz;
        private Field[] fields;
        private String tableName;
        private String[] columns;

        public EntityDefinition(Class<T> clazz) {
            this.clazz = clazz;
            fields = clazz.getDeclaredFields();
            columns = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                columns[i] = fields[i].getName();
            }
            tableName = getTableName();
        }

        private String getTableName() {
            if (tableName == null) {
                Table annotation = clazz.getAnnotation(Table.class);
                if (annotation == null) {
                    log.error("bean {} 是未知映射关系的 ", clazz.getName());
                    throw new IllegalArgumentException();
                }
                if (StrUtil.isBlank(annotation.name())) {
                    log.error("bean {} 标注的 {}#name 是不合法的 ", clazz.getName(), annotation.getClass().getName());
                    throw new IllegalArgumentException();
                }
                tableName = annotation.name();
            }
            return tableName;
        }
    }

}
