package cn.leaf.exercise.common;

/**
 * aop service impl
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/13 17:24
 */
public class AopServiceImpl implements AopService {
    @Override
    public void execute() {
        System.out.println("aop service execute .... ");
    }

    @Override
    public void run() {
        System.out.println("aop service run .... ");
    }

    @Override
    public void start() {
        System.out.println("aop service start .... ");
    }
}
