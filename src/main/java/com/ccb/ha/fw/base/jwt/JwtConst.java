package com.ccb.ha.fw.base.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtConst {
    //Header的名称
    private String jwtHeader = "JWT";
    //加密密码
    private String authCode = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";
    //超时分钟数（默认120分钟）
    private int exp = 120;
    //iss值
    private String iss = "www.study.com";
    //主体
    private String sub = "www.study.com";
    //接收者
    private String aud = "www.study.com";
    //客户端ID
    private String cid = "client_ID";

    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "JwtConst{" +
                "jwtHeader='" + jwtHeader + '\'' +
                ", auth='" + authCode + '\'' +
                ", exp=" + exp +
                ", iss='" + iss + '\'' +
                ", sub='" + sub + '\'' +
                ", aud='" + aud + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
