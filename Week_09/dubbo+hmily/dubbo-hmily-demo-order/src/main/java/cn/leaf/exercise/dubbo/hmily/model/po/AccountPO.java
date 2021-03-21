package cn.leaf.exercise.dubbo.hmily.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author 李克国
 * @date 2021/3/21
 */
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountPO {

    private String account;

    private Integer sources;

    private BigDecimal balance;

    private BigDecimal freezeBalance;

}
