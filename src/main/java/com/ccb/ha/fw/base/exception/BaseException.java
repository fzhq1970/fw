package com.ccb.ha.fw.base.exception;

import com.ccb.ha.fw.base.StaticConst;

public class BaseException extends RuntimeException {
    private int code;

    //严重的未知异常
    public static final BaseException EX_UNKNOWN =
            new BaseException();

    public static final BaseException EX_LOGIN =
            new BaseException(StaticConst.ERROR_CODE, "您没有登录或者登录超时");

    public static final BaseException EX_TOKEN =
            new BaseException(StaticConst.ERROR_CODE, "令牌验证失败，请重新登录申请");

    public static final BaseException EX_RELOGIN =
            new BaseException(StaticConst.ERROR_CODE, "用户已经重新登录，请重新获得授权");

    public BaseException() {
        super(StaticConst.UNKNOWN_MESSAGE);
        this.code = StaticConst.ERROR_CODE;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, Throwable th) {
        super(th);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s[%d]",
                this.getMessage(),
                this.code);
    }
}
