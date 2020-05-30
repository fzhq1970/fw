package com.ccb.ha.fw.base.handler;

import com.ccb.ha.fw.base.Result;
import com.ccb.ha.fw.base.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DefaultExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleAuthorizationException(Exception e) {
        if (log.isDebugEnabled()) {
            log.debug("handleAuthorizationException：[{}].", e.getMessage());
        }
        return Result.error(e);
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result handleBaseException(BaseException e) {
        if (log.isDebugEnabled()) {
            log.debug("handleBaseException ：[{}].", e.toString());
        }
        return Result.error(e);
    }
}
