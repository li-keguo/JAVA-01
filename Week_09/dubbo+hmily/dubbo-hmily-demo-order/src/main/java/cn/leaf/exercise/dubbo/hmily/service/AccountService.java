package cn.leaf.exercise.dubbo.hmily.service;

import cn.leaf.exercise.dubbo.hmily.model.po.Account;
import org.dromara.hmily.annotation.Hmily;

/**
 * @author 李克国
 * @date 2021/3/21
 */
public interface AccountService {

    /**
     * 转账：对from账户进行支付，对to账户进行存入
     *
     * @return success
     */
    @Hmily
    boolean transfer(Account from, Account to);

    /**
     * 转转账确认：执行对from账户支付取消，同时执行to账户存入取消
     *
     * @param from 来源：转出账户,余额为支付金额
     * @param to   目标：转入账户，余额为存入金额
     * @return success or fail
     */
    boolean transferConfirm(Account from, Account to);

    /**
     * 转账取消：执行对from账户支付取消，同时执行to账户存入取消
     *
     * @param from 来源：转出账户,余额为支付金额
     * @param to   目标：转入账户，余额为存入金额
     * @return success or fail
     */
    boolean transferCancel(Account from, Account to);


    /**
     * 付钱：对账户中对余额扣钱，添加到冻结额中
     *
     * @param account 账户
     * @return success or fail
     */
    @Hmily
    boolean pay(Account account);


    /**
     * 付钱成功确认：对账户中对冻结扣减，余额不变
     *
     * @param account 账户
     * @return success or fail
     */
    boolean payConfirm(Account account);

    /**
     * 付钱失败补偿：对账户中对冻结扣减，余额补偿
     *
     * @param account 账户：其中余额作为支付的金额
     * @return success or fail
     */
    boolean payCancel(Account account);


    /**
     * 存入：对账户中冻结额度增加，
     *
     * @param account 账户：其中余额作为存入对金额
     * @return success or fail
     */
    @Hmily
    boolean deposit(Account account);

    /**
     * 存入：对账户中冻结额度减少，同时对余额增加，
     *
     * @param account 账户：其中余额作为存入对金额
     * @return success or fail
     */
    boolean depositConfirm(Account account);

    /**
     * 存入：对账户中冻结额度减少
     *
     * @param account 账户：其中余额作为存入对金额
     * @return success or fail
     */
    boolean depositCancel(Account account);
}
