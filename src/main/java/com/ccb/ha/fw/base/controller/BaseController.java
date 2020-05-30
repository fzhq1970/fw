package com.ccb.ha.fw.base.controller;

import com.ccb.ha.fw.base.jwt.JwtConst;
import com.ccb.ha.fw.base.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
    private static final Logger log =
            LoggerFactory.getLogger(BaseController.class);
    @Autowired
    JwtConst jwtConst;
    @Autowired
    JwtUtil jwtUtil;

    protected String getCurrentUsername() {
//        if (log.isDebugEnabled()) {
//            log.debug("BaseController : getCurrentUsername");
//        }
//        Subject subject = SecurityUtils.getSubject();
//        if (log.isDebugEnabled()) {
//            log.debug("BaseController : getCurrentUsername = [{}].", subject);
//        }
//        if (subject == null) {
//            //没有登录或者会话失效
//            throw BaseException.EX_LOGIN;
//        }
//        Object principal = subject.getPrincipal();
//        if (log.isDebugEnabled()) {
//            log.debug("BaseController : principal = [{}].", principal);
//        }
//        if (principal == null) {
//            //没有登录或者会话失效
//            //throw BaseException.EX_LOGIN;
//        }
//        return principal.toString();
        return "";
    }
}
