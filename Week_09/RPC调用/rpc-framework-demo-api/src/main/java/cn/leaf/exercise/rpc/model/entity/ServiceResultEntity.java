package cn.leaf.exercise.rpc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李克国
 * @date 2021/3/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResultEntity<T> {

    private int status;

    private String message;

    private T data;

    private Throwable throwable;

    public static <T> ServiceResultEntity<T>  result(T data){
        ServiceResultEntity<T> result = new ServiceResultEntity<>();
        result.setData(data);
        return result;
    }
}
