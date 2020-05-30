package com.ccb.ha.fw.base.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ccb.ha.fw.base.entity.AdminUser;
import com.ccb.ha.fw.base.exception.BaseException;
import com.ccb.ha.fw.util.JsonUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Autowired
    JwtConst jwtConst = new JwtConst();

    /**
     * 生成token
     *
     * @param claims
     * @return
     */
    public String create(Map<String, String> claims, String username,
                         String password) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(
                    this.jwtConst.getAuthCode());
            //设置头信息
            HashMap<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", algorithm.getName());
            JWTCreator.Builder builder = JWT.create()
                    .withHeader(header)
                    .withIssuer(this.jwtConst.getIss())
                    .withAudience(username)
                    //设置过期时间为2小时
                    .withExpiresAt(DateUtils.addMinutes(new Date(),
                            this.jwtConst.getExp()));
            claims.forEach(builder::withClaim);
            return builder.sign(algorithm);
        } catch (IllegalArgumentException e) {
            throw new Exception("生成token失败");
        }
    }

    /**
     * Claim有一个bug，通用的asString只能处理String字段，
     * int、long等都会出现问题
     *
     * @param claim
     * @return
     */
    private String claim2String(Claim claim) {
        if (claim == null) {
            return null;
        }
        Integer iV = claim.asInt();
        if (iV != null) {
            return iV.toString();
        }
        Long lV = claim.asLong();
        if (iV != null) {
            return iV.toString();
        }
        Double dV = claim.asDouble();
        if (dV != null) {
            return dV.toString();
        }
        String value = claim.asString();
        return value;
    }

    /**
     * 验证jwt，并返回数据
     */
    public Map<String, String> verify(String token) throws Exception {
        Algorithm algorithm;
        Map<String, Claim> map;
        try {
            algorithm = Algorithm.HMAC256(this.jwtConst.getAuthCode());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.jwtConst.getIss())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();
        } catch (Exception e) {
            throw BaseException.EX_TOKEN;
        }
        Map<String, String> resultMap = new HashMap<>(map.size());
        Iterator<String> its = map.keySet().iterator();
        map.forEach((k, v) -> resultMap.put(k, claim2String(v)));
        return resultMap;
    }

    /**
     * 获取被授权人代码
     *
     * @param token
     * @return
     */
    public String getAudience(String token) {
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            if (log.isDebugEnabled()) {
                log.debug("getAudience : userId = [{}}", userId);
            }
        } catch (JWTDecodeException j) {
            return null;
        }
        return userId;
    }

    /**
     * 生成token
     *
     * @param user
     * @return
     */
    public String getToken(AdminUser user) {
        Map<String, String> claims = new HashMap<>();
        String token = null;
        String username = user.getUsername();
        String password = user.getPassword();
        String name = user.getName();
        claims.put("username", username);
        claims.put("name", name);
        try {
            token = this.create(claims, username, password);
        } catch (Exception e) {
            log.error("getToken : [{}}.", e.getMessage());
        }
        return token;
    }

    /**
     * @throws Exception
     */
    private void jwtTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "10001");
        map.put("username", "zhangsan");
        map.put("name", "张三");
        String token = create(map, "zhangsan", "password");
        System.out.println(token);
        Map<String, String> res = verify(token);
        System.out.println(JsonUtils.obj2Json(res));
    }

    /**
     * @param args
     */
    public static void main(String args[]) throws Exception {
        JwtUtil jwt = new JwtUtil();
        jwt.jwtTest();
    }
}
