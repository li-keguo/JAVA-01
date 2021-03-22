package cn.leaf.exercise.dubbo.hmily.service.impl;

import cn.leaf.exercise.dubbo.hmily.model.AccountStatus;
import cn.leaf.exercise.dubbo.hmily.mapper.AccountMapper;
import cn.leaf.exercise.dubbo.hmily.model.po.Account;
import cn.leaf.exercise.dubbo.hmily.model.po.AccountPO;
import cn.leaf.exercise.dubbo.hmily.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 简单转账模型实现，hmily TCC事务模型应用
 *
 * @author 李克国
 * @date 2021/3/21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleAccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    /**
     * 远程账户调用
     */
    private final AccountService remoteAccountService;

    @Transactional(rollbackFor = Exception.class)
    @HmilyTCC(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    @Override
    public boolean transfer(Account from, Account to) {
        AccountPO formAccount = accountMapper.selectByPrimaryKey(from.getAccount());
        // check account status, ignore account not found
        if (from.getBalance().equals(to.getBalance())) {
            // 转出与受入不一致，此处忽略，将以转出为准
            to.setBalance(from.getBalance());
            log.warn("转账【from account {{}} to account {{}} pay {{}} 】transfer 转出与受入不一致，此处忽略，将以转出为准,忽略值为：{}",
                    from.getAccount(), to.getAccount(), from.getBalance(), to.getBalance());
        }
        try{
            pay(from);
            log.info("转账【from account {{}} to account {{}} pay {{}} 】transfer 第一阶段：from account 支付完成，该金额将暂时进入冻结状态",
                    from.getAccount(), to.getAccount(), from.getBalance());
        }finally {
            // 无论支付是否成功，都要对to账户存入，事务由补偿机制保障
            AccountPO toAccount = accountMapper.selectByPrimaryKey(to.getAccount());
            // check account status,
            if (toAccount == null || AccountStatus.VIRTUAL.getSources().equals(toAccount.getSources())) {
                remoteAccountService.deposit(to);
                // 假设：账户为vip，跨行转账不收手续费
                log.info("转账【from account {{}} to account {{}} deposit {{}} 】transfer 第二阶段：to account 存入完成，该次存入为跨行转账，收取手续费:{}",
                        from.getAccount(), to.getAccount(), from.getBalance(), 0);
            } else {
                deposit(to);
                log.info("转账【from account {{}} to account {{}} deposit {{}} 】transfer 第二阶段：to account 存入完成,存入金额暂时冻结",
                        from.getAccount(), to.getAccount(), from.getBalance());
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferConfirm(Account from, Account to) {
        payConfirm(from);
        log.info("转账【from account {{}} to account {{}} pay {{}} 】transfer confirm 第一阶段：to account 支付完成：冻结扣减",
                from.getAccount(), to.getAccount(), from.getBalance());
        AccountPO toAccount = accountMapper.selectByPrimaryKey(to);
        if (toAccount != null || !AccountStatus.VIRTUAL.getSources().equals(toAccount.getSources())) {
            depositConfirm(to);
            log.info("转账【from account {{}} to account {{}} deposit {{}} 】transfer confirm  第二阶段：to account 存入完成：冻结解除",
                    from.getAccount(), to.getAccount(), from.getBalance());
        }
        // remote ignore
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean transferCancel(Account from, Account to) {
        payCancel(from);
        log.info("转账【from account {{}} to account {{}} pay {{}} 】transfer cancel 第一阶段：to account 支付失败：冻结解除，余额补偿",
                from.getAccount(), to.getAccount(), from.getBalance());
        AccountPO toAccount = accountMapper.selectByPrimaryKey(to);
        if (toAccount != null || !AccountStatus.VIRTUAL.getSources().equals(toAccount.getSources())) {
            depositCancel(to);
            log.info("转账【from account {{}} to account {{}} deposit {{}} 】transfer confirm  第二阶段：to account 存入失败：冻结扣减",
                    from.getAccount(), to.getAccount(), from.getBalance());
        }
        // remote ignore
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @HmilyTCC(confirmMethod = "payConfirm", cancelMethod = "payCancel")
    @Override
    public boolean pay(Account account) {
        AccountPO accountPO = accountMapper.selectByPrimaryKey(account.getAccount());
        // ignore 账户不存在，余额不足 等
        // 余额扣减
        accountPO.setBalance(accountPO.getBalance().subtract(account.getBalance()));
        // 冻结额增加
        accountPO.setFreezeBalance(accountPO.getFreezeBalance().add(account.getBalance()));

        accountMapper.updateByPrimaryKey(accountPO);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean payConfirm(Account account) {
        AccountPO accountPO = accountMapper.selectByPrimaryKey(account.getAccount());
        // ignore 账户不存在，等
        // 冻结额扣除
        accountPO.setFreezeBalance(accountPO.getFreezeBalance().subtract(account.getBalance()));

        accountMapper.updateByPrimaryKey(accountPO);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean payCancel(Account account) {
        AccountPO accountPO = accountMapper.selectByPrimaryKey(account.getAccount());
        // ignore 账户不存在 等
        // 余额补偿
        accountPO.setBalance(accountPO.getBalance().add(account.getBalance()));
        // 冻结额扣减
        accountPO.setFreezeBalance(accountPO.getFreezeBalance().subtract(account.getBalance()));

        accountMapper.updateByPrimaryKey(accountPO);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @HmilyTCC(confirmMethod = "depositConfirm", cancelMethod = "depositCancel")
    @Override
    public boolean deposit(Account account) {
        AccountPO accountPO = accountMapper.selectByPrimaryKey(account.getAccount());
        // ignore 账户不存在 等
        // 冻结额增加
        accountPO.setFreezeBalance(accountPO.getFreezeBalance().add(account.getBalance()));

        accountMapper.updateByPrimaryKey(accountPO);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean depositConfirm(Account account) {
        AccountPO accountPO = accountMapper.selectByPrimaryKey(account.getAccount());
        // ignore 账户不存在，余额不足 等
        // 冻结额扣减
        accountPO.setFreezeBalance(accountPO.getFreezeBalance().subtract(account.getBalance()));
        // 余额额增加
        accountPO.setBalance(accountPO.getBalance().add(account.getBalance()));

        accountMapper.updateByPrimaryKey(accountPO);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean depositCancel(Account account) {
        AccountPO accountPO = accountMapper.selectByPrimaryKey(account.getAccount());
        // ignore 账户不存在，余额不足 等
        // 冻结额增加
        accountPO.setFreezeBalance(accountPO.getFreezeBalance().subtract(account.getBalance()));

        accountMapper.updateByPrimaryKey(accountPO);
        return true;
    }
}
