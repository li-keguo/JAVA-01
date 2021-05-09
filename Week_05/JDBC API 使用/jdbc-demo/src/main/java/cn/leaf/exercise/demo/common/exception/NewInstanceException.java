package cn.leaf.exercise.demo.common.exception;

/**
 * @author com.karl lee
 * @Date 2019/2/28
 */
public class NewInstanceException extends StudyException {
    public NewInstanceException() {
        super();
    }


    public NewInstanceException(String message) {
        super(message);
    }
}
