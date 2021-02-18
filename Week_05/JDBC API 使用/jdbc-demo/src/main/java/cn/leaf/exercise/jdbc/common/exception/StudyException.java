package cn.leaf.exercise.jdbc.common.exception;


import cn.leaf.exercise.jdbc.common.enums.ExceptionEnums;

/**
 * @author karl lee
 * @Date 2019/6/18
 */
public class StudyException extends RuntimeException {

    private ExceptionEnums exceptionEnums;
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public StudyException() {
    }

    public StudyException(String message) {
        super(message);
    }
    public StudyException(ExceptionEnums ExceptionEnums) {
        this.exceptionEnums = exceptionEnums;
    }


    public ExceptionEnums getExceptionEnums() {
        return exceptionEnums;
    }

    public void setExceptionEnums(ExceptionEnums ExceptionEnums) {
        this.exceptionEnums = exceptionEnums;
    }
}
