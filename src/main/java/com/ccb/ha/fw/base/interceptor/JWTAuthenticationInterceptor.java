package com.ccb.ha.fw.base.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ccb.ha.fw.base.annotations.PassToken;
import com.ccb.ha.fw.base.annotations.UserLoginToken;
import com.ccb.ha.fw.base.cache.CacheLoginInfo;
import com.ccb.ha.fw.base.cache.LoginInfo;
import com.ccb.ha.fw.base.entity.AdminUser;
import com.ccb.ha.fw.base.exception.BaseException;
import com.ccb.ha.fw.base.jwt.JwtConst;
import com.ccb.ha.fw.base.jwt.JwtUtil;
import com.ccb.ha.fw.base.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JWTAuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger log =
            LoggerFactory.getLogger(JWTAuthenticationInterceptor.class);
    @Autowired
    AdminUserService adminUserService;
    @Autowired
    JwtConst jwtConst;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CacheLoginInfo cacheLoginInfo;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object object) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("onPreHandle : request = [{}}, response = [{}], mappedValue = [{}].",
                    httpServletRequest, httpServletResponse, object);
            log.debug("jwtConst : [{}].", this.jwtConst);
        }
        String token = httpServletRequest.getHeader(this.jwtConst.getJwtHeader());
        if (log.isDebugEnabled()) {
            log.debug("onPreHandle : token = [{}}", token);
        }
        // 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (log.isDebugEnabled()) {
            log.debug("onPreHandle : method = [{}}", method);
        }
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            if (log.isDebugEnabled()) {
                log.debug("onPreHandle : method = [{}}", PassToken.class);
            }
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (log.isDebugEnabled()) {
                log.debug("onPreHandle : UserLoginToken = [{}}", UserLoginToken.class);
            }
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw BaseException.EX_LOGIN;
                }
                // 获取 token 中的 user id
                String userId = this.jwtUtil.getAudience(token);
                // 执行认证
                if (userId == null) {
                    throw BaseException.EX_LOGIN;
                }
                LoginInfo loginInfo = this.cacheLoginInfo.loadLoginInfo(userId);
                if (loginInfo == null) {
                    throw BaseException.EX_TOKEN;
                }
                //用户信息
                AdminUser user = loginInfo.getUser();
                if (!token.equals(loginInfo.getToken())) {
                    //两个至不一样，说明已经发生了变化
                    if (log.isDebugEnabled()) {
                        log.debug("用户已经重新登录，token已经失效");
                    }
                    throw BaseException.EX_RELOGIN;
                }
                // 验证 token
                try {
                    this.jwtUtil.verify(token);
                } catch (JWTVerificationException e) {
                    throw BaseException.EX_TOKEN;
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o,
                                Exception e) throws Exception {
    }

}