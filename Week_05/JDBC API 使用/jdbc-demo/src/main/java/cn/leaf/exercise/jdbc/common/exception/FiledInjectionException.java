package cn.leaf.exercise.jdbc.common.exception;

/**
 * @author karl lee
 * @Date 2019/7/9
 */
public class FiledInjectionException extends CreateBeanException {


    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public FiledInjectionException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FiledInjectionException(String message) {
        super(message);
    }
}
