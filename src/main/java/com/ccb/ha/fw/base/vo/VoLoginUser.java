package com.ccb.ha.fw.base.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class VoLoginUser implements Serializable {
    private static final Logger log =
            LoggerFactory.getLogger(VoLoginUser.class);
    private String username;
    private String password;
    private String checkCode;
    private String mobile;
    private String email;
    private boolean rememberMe;

    public VoLoginUser() {

    }

    public VoLoginUser(String username, String password,
                       String checkCode, String mobile,
                       String email,boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.checkCode = checkCode;
        this.mobile = mobile;
        this.email = email;
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "VoLoginUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", checkCode='" + checkCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", rememberMe='" + rememberMe + '\'' +
                '}';
    }
}
