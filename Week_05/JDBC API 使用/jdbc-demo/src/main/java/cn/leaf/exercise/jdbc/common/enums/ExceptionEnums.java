package cn.leaf.exercise.jdbc.common.enums;


/**
 * @author karl lee
 * @Date 2019/5/19
 */

public enum ExceptionEnums {

    /**
     *
     */
    PRIMARY_KEY_IS_NULL(-1, "主键参数不能为空"),
    /**
     *
     */
    SQL_EXCEPTION(-2, "系统异常"),
    /**
     *
     */
    FACTORY_CREATE_BEAN_ERROR(-3, "创建bean异常"),
    ;


    private Integer code;
    private String msg;

    ExceptionEnums(Integer code) {
        this.code = code;
    }

    ExceptionEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
