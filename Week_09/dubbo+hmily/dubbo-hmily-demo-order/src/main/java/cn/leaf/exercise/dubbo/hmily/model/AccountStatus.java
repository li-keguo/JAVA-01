package cn.leaf.exercise.dubbo.hmily.model;

import lombok.*;

/**
 * @author 李克国
 * @date 2021/3/21
 */
@AllArgsConstructor
@NoArgsConstructor
public enum AccountStatus {
    /**
     * 虚拟账户：来自远程系统，或者其他银行账户：无具体信息，
     */
    VIRTUAL(1);

    /**
     * 来源
     */
    @Getter
    private Integer sources;


}
