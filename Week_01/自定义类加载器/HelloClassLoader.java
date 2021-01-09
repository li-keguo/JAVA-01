
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * HelloClassLoader
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/1/8 9:43
 */
public abstract class HelloClassLoader extends ClassLoader {

    public static final String SUFFIX_CLASS = ".class";
    public static final String SUFFIX_XLASS = ".xlass";

    protected String suffix;

    public HelloClassLoader(String suffix) {
        this.suffix = suffix;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        // 读取base64加密的.class字符串 执行hello方法
        test(new Base64HelloClassLoader(SUFFIX_CLASS));
        // 读取base64加密的.xlass字符串 执行hello方法
        test(new Base64HelloClassLoader(SUFFIX_XLASS));
        String classPath = System.getProperty("user.dir") + File.separator + "Hello";
        // 读取.class文件 执行hello方法
        test(new FileHelloClassLoader(SUFFIX_CLASS, classPath));
        // 读取.xlass文件 执行hello方法
        test(new FileHelloClassLoader(SUFFIX_XLASS, classPath));
    }

    private static void test(HelloClassLoader helloClassLoader) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> hello = helloClassLoader.findClass("Hello");
        Object o = hello.newInstance();
        Method htlloMethod = hello.getMethod("hello");
        htlloMethod.invoke(o);
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getClassBytes(name);
        if (SUFFIX_CLASS.equals(suffix)) {
            System.out.println(new String(bytes));
            return defineClass(name, bytes, 0, bytes.length);
        }
        if (SUFFIX_XLASS.equals(suffix)) {
            deCodeBytes(bytes);
            System.out.println(new String(bytes));
            return defineClass(name, bytes, 0, bytes.length);
        }
        throw new IllegalArgumentException("不合法的类文件后缀");
    }

    protected abstract byte[] getClassBytes(String name) throws ClassNotFoundException;

    private void deCodeBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ~bytes[i];
        }
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    static class FileHelloClassLoader extends HelloClassLoader {

        private String helloClassPath;

        public FileHelloClassLoader(String helloClassPath) {
            super(SUFFIX_CLASS);
            this.helloClassPath = helloClassPath;
        }

        public FileHelloClassLoader(String suffix, String helloClassPath) {
            super(suffix);
            this.helloClassPath = helloClassPath;
        }

        @Override
        protected byte[] getClassBytes(String name) throws ClassNotFoundException {
            try (FileInputStream reader = new FileInputStream(new File(helloClassPath + File.separator + name + suffix))) {
                byte[] bytes = new byte[reader.available()];
                reader.read(bytes);
                return bytes;
            } catch (FileNotFoundException e) {
                throw new ClassNotFoundException(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class Base64HelloClassLoader extends HelloClassLoader {
        public Base64HelloClassLoader(String suffix) {
            super(suffix);
        }

        @Override
        protected byte[] getClassBytes(String name) throws ClassNotFoundException {
            if (SUFFIX_CLASS.equals(suffix)) {
                return Base64.getDecoder().decode("yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygp" +
                        "VgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAVoZWxsbwEAClNvdXJjZUZpbGUB" +
                        "AApIZWxsby5qYXZhDAAHAAgHABYMABcAGAEAE0hlbGxvLCBjbGFzc0xvYWRlciEH" +
                        "ABkMABoAGwEABUhlbGxvAQAQamF2YS9sYW5nL09iamVjdAEAEGphdmEvbGFuZy9T" +
                        "eXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1By" +
                        "aW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAAUA" +
                        "BgAAAAAAAgABAAcACAABAAkAAAAdAAEAAQAAAAUqtwABsQAAAAEACgAAAAYAAQAA" +
                        "AAEAAQALAAgAAQAJAAAAJQACAAEAAAAJsgACEgO2AASxAAAAAQAKAAAACgACAAAA" +
                        "BAAIAAUAAQAMAAAAAgAN");
            }
            if (SUFFIX_XLASS.equals(suffix)) {
                return Base64.getDecoder().decode("NQFFQf///8v/4/X/+f/x9v/w/+/3/+71/+3/7Pj/6/j/6v7/+cOWkZaLwf7//NfW" +
                        "qf7/+7yQm5r+//CzlpGasYqSnZqNq56dk5r+//qXmpOTkP7/9ayQio2cmrmWk5r+" +
                        "//W3mpOTkNGVnome8//4//f4/+nz/+j/5/7/7Leak5OQ09+ck56MjLOQnpuajd74" +
                        "/+bz/+X/5P7/+reak5OQ/v/vlZ6JntCTnpGY0LCdlZqci/7/75WeiZ7Qk56RmNCs" +
                        "hoyLmpL+//yQiov+/+qzlZ6JntCWkNCvjZaRi6yLjZqeksT+/+yVnome0JaQ0K+N" +
                        "lpGLrIuNmp6S/v/4j42WkYuTkf7/6tezlZ6JntCTnpGY0KyLjZaRmMTWqf/e//r/" +
                        "+f///////f/+//j/9//+//b////i//7//v////rVSP/+Tv////7/9f////n//v//" +
                        "//7//v/0//f//v/2////2v/9//7////2Tf/97fxJ//tO/////v/1////9f/9////" +
                        "+//3//r//v/z/////f/y");
            }
            throw new ClassNotFoundException();
        }
    }
}