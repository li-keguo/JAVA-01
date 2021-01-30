package cn.leaf.exercise.nio.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认过滤器管理器
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @Date 2021/1/30 15:47
 * @description 默认过滤器管理器
 */
@Builder
@Data
@AllArgsConstructor
public class DefaultHttpRequestFilterHandlerImpl implements HttpRequestFilterHandler {

    @Builder.Default
    private final List<HttpRequestFilter> filters = new ArrayList<>();

    @Override
    public void add(HttpRequestFilter filter) {
        filters.add(filter);
    }

    @Override
    public List<HttpRequestFilter> getFilters() {
        return filters;
    }
}
