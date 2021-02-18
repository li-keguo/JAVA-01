package cn.leaf.exercise.singleton;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

/**
 * 单例的写法 demo
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/13 18:12
 */
public class SingletonDemo {

    public static void main(String[] args) {
        System.out.println("Singleton1 is singleton : " + (Singleton1.getInstance() == Singleton1.getInstance()));
        System.out.println("Singleton2 is singleton : " + (Singleton2.getInstance() == Singleton2.getInstance()));
        System.out.println("Singleton3 is singleton : " + (Singleton3.getInstance() == Singleton3.getInstance()));
        System.out.println("Singleton4 is singleton : " + (Singleton4.getInstance() == Singleton4.getInstance()));
        System.out.println("Singleton5 is singleton : " + (Singleton5.getInstance() == Singleton5.getInstance()));
        System.out.println("Singleton6 is singleton : " + (Singleton6.getInstance() == Singleton6.getInstance()));
        System.out.println("Singleton7 is singleton : " + (Singleton7.instance == Singleton7.instance));
    }

    static class Singleton1 {
        private static Singleton1 instance;

        private Singleton1() {
        }

        public static Singleton1 getInstance() {
            if (instance == null) {
                instance = new Singleton1();
            }
            return instance;
        }
    }

    static class Singleton2 {
        private static final Singleton2 instance = new Singleton2();

        private Singleton2() {
        }

        public static Singleton2 getInstance() {
            return instance;
        }
    }

    static class Singleton3 {
        private static Singleton3 instance;

        private static boolean isInstance = false;

        private Singleton3() {
        }

        public static Singleton3 getInstance() {
            if (!isInstance) {
                instance = new Singleton3();
                isInstance = true;
            }
            return instance;
        }
    }

    static class Singleton4 {


        private Singleton4() {
        }

        public static Singleton4 getInstance() {
            return Builder.instance;
        }

        static class Builder {
            private static Singleton4 instance = new Singleton4();
        }
    }

    static class Singleton5 {
        private static Singleton5 instance;

        private Singleton5() {
        }

        public static Singleton5 getInstance() {
            if (instance == null) {
                synchronized (Singleton5.class) {
                    if (instance == null) {
                        instance = new Singleton5();
                    }
                }
            }
            return instance;
        }
    }

    static class Singleton6 {
        private static List<Singleton6> instance = new ArrayList<>();

        private Singleton6() {
        }

        public static Singleton6 getInstance() {
            if (instance.size() == 0) {
                instance.add(new Singleton6());
            }
            return instance.get(0);
        }
    }

    enum Singleton7 {
        /**
         * 实例
         */
        instance;

        private Object readResolve() throws ObjectStreamException {
            return instance;
        }
    }
}
