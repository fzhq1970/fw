package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserDao extends JpaRepository<AdminUser, Long> {
    /**
     * 使用用户名或者邮件地址或者手机号码读取用户
     *
     * @param username
     * @return
     */
    AdminUser findByUsername(String username);

    /**
     * 使用用户名或者邮件地址或者手机号码读取用户
     *
     * @param username
     * @return
     */
    AdminUser findByUsernameOrEmailOrPhone(String username,
                                           String mail, String phone);

    /**
     * 使用用户名或者邮件地址或者手机号码，配合用户密码读取用户
     *
     * @param username
     * @param password
     * @return
     */
    AdminUser getByUsernameAndPassword(String username, String password);
}
