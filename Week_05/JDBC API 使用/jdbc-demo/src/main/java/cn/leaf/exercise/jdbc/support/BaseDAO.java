package cn.leaf.exercise.jdbc.support;

import java.util.List;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/16 16:14
 */
public interface BaseDAO<T> {


    List<T> selectAll();

    List<T> selectByLimit(int limit);

    List<T> selectByLimit(int offset, int limit);


    Integer update(T updateBody);

    void insert(T insertBody);


    void inserts(List<T> insertBodys);
}
