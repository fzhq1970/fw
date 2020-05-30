package com.ccb.ha.fw.base;

import com.ccb.ha.fw.base.exception.BaseException;

import java.io.Serializable;

public class Result implements Serializable {


    /**
     * 成功处理，但是没有返回结果
     */
    public static final Result SUCCESS =
            new Result(StaticConst.SUCCESS_CODE, StaticConst.SUCCESS_MESSAGE);

    public static final Result UNKNOWN =
            new Result(StaticConst.ERROR_CODE, StaticConst.UNKNOWN_MESSAGE);

    /**
     * 成功处理返回，带返回结果
     *
     * @param data
     * @return
     */
    public static final Result success(Object data) {
        return new Result(StaticConst.SUCCESS_CODE,
                StaticConst.SUCCESS_MESSAGE,
                data);
    }

    /**
     * 失败处理返回
     *
     * @return
     */
    public static Result error(String message) {
        return new Result(StaticConst.ERROR_CODE,
                message);
    }

    /**
     * 失败处理返回，带返回结果
     *
     * @param data
     * @return
     */
    public static Result error(String message, Object data) {
        return new Result(StaticConst.ERROR_CODE,
                message, data);
    }

    /**
     * 根据异常生成返回信息
     *
     * @param be
     * @return
     */
    public static Result error(BaseException be) {
        return new Result(be.getCode(),
                be.getMessage(), null);
    }

    /**
     * @param be
     * @return
     */
    public static Result error(Exception be) {
        return new Result(StaticConst.ERROR_CODE,
                be.getMessage(), null);
    }

    private int code;
    private String message;
    private Object data;

    public Result() {
        this(StaticConst.SUCCESS_CODE, "success", null);
    }

    public Result(int code, String message) {
        this(code, message, null);
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
