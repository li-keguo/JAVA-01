package cn.leaf.exercise.dubbo.hmily.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author 李克国
 * @date 2021/3/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String name;

    private String userId;
    /**
     * 账户
     */
    @Id
    private String account;

    /**
     * 余额
     */
    private BigDecimal balance;
}
