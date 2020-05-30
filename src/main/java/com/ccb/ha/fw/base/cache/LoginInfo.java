package com.ccb.ha.fw.base.cache;

import com.ccb.ha.fw.base.entity.AdminUser;

public class LoginInfo {

    private String token;
    private AdminUser user;

    public LoginInfo(String token, AdminUser user) {
        this.token = token;
        this.user = user;
    }

    public LoginInfo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AdminUser getUser() {
        return user;
    }

    public void setUser(AdminUser user) {
        this.user = user;
    }
}
