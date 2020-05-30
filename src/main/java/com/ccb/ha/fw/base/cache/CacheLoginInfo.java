package com.ccb.ha.fw.base.cache;

import com.ccb.ha.fw.base.jwt.JwtConst;
import com.ccb.ha.fw.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheLoginInfo {
    public static final long MS = 1L * 60;
    private static final Logger log =
            LoggerFactory.getLogger(CacheLoginInfo.class);
    final
    CacheObject cacheObject;
    final
    JwtConst jwtConst;
    @Value("${spring.redis.key.login.token.path}")
    String path;

    public CacheLoginInfo(CacheObject cacheObject, JwtConst jwtConst) {
        this.cacheObject = cacheObject;
        this.jwtConst = jwtConst;
    }

    /**
     * 从缓存中读取用户登录信息
     *
     * @param key
     * @return
     */
    public LoginInfo loadLoginInfo(String key) {
        if (log.isDebugEnabled()) {
            log.debug("loadLoginInfo : key = [{}}", key);
        }
        if ((key == null) || (key.isEmpty())) {
            return null;
        }
        String userLoginString = this.cacheObject.get(this.path +
                ":" + key);
        if (log.isDebugEnabled()) {
            log.debug("loadLoginInfo : userLoginString = [{}}", userLoginString);
        }
        if (userLoginString == null) {
            return null;
        }
        LoginInfo loginInfo = JsonUtils.json2Pojo(userLoginString, LoginInfo.class);
        if (log.isDebugEnabled()) {
            log.debug("loadLoginInfo : loginInfo = [{}}", loginInfo);
        }
        return loginInfo;
    }

    /**
     * 保存用户登录信息到缓存中
     *
     * @param login
     */
    public void storeLoginInfo(LoginInfo login) {
        if (log.isDebugEnabled()) {
            log.debug("storeLoginInfo : login = [{}}", login);
        }
        if (login == null) {
            return;
        }
        String json = JsonUtils.obj2Json(login);
        if (log.isDebugEnabled()) {
            log.debug("storeLoginInfo : json = [{}}", json);
        }
        //redis保存
        this.cacheObject.put(this.path + ":"
                        + login.getUser().getUsername(),
                json, MS * this.jwtConst.getExp());
    }

    /**
     * 清理用户信息
     *
     * @param key
     */
    public void clean(String key) {
        if (log.isDebugEnabled()) {
            log.debug("clean : key = [{}}", key);
        }
        this.cacheObject.remove(this.path + ":"
                + key);
    }

    /**
     * 更新用户信息，同时返回老数据
     *
     * @param login
     * @return
     */
    public LoginInfo updateLoginInfo(LoginInfo login) {
        if (log.isDebugEnabled()) {
            log.debug("updateLoginInfo : login = [{}}", login);
        }
        if (login == null) {
            return null;
        }
        LoginInfo old = this.loadLoginInfo(login.getUser().getUsername());
        if (log.isDebugEnabled()) {
            log.debug("updateLoginInfo : old = [{}}", old);
        }
        this.storeLoginInfo(login);
        return old;
    }
}
