package cn.leaf.freemq;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * @author 李克国
 * @date 2021/5/4
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String comet = scanner.nextLine();
        // 团队
        String team = scanner.nextLine();

        if (calCode(comet) % 47 == calCode(team) % 47) {
            System.out.println("GO");
        } else {
            System.out.println("STAR");
        }
    }

    private static Long calCode(String str) {
        long rs = 1;
        for (int i = 0; i < str.length(); i++) {
            rs *= (str.charAt(i) - 'A' + 1);
        }
        return rs;
    }

    @Test
    public void test() {
        Long aLong = calCode("USACO");
        System.out.println(aLong);
    }
}
