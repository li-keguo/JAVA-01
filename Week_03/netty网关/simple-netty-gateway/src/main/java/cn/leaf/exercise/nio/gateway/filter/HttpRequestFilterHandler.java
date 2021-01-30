package cn.leaf.exercise.nio.gateway.filter;

import java.util.List;

/**
 * 过滤器管理器
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/30 11:38
 * @description 过滤器管理器
 */
public interface HttpRequestFilterHandler {

    /**
     * 添加过滤器,注意添加顺序
     *
     * @param filter 过滤器
     */
    void add(HttpRequestFilter filter);

    /**
     * 获取过滤器集合
     *
     * @return 过滤器
     */
    List<HttpRequestFilter> getFilters();
}
