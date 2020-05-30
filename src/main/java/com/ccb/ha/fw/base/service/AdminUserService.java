package com.ccb.ha.fw.base.service;

import com.ccb.ha.fw.base.dao.AdminRoleDao;
import com.ccb.ha.fw.base.dao.AdminUserDao;
import com.ccb.ha.fw.base.dao.AdminUserRoleDao;
import com.ccb.ha.fw.base.entity.AdminRole;
import com.ccb.ha.fw.base.entity.AdminUser;
import com.ccb.ha.fw.base.entity.AdminUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminUserService {
    private static final Logger log =
            LoggerFactory.getLogger(AdminUserService.class);
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    AdminUserRoleDao adminUserRoleDao;
    @Autowired
    AdminRoleDao adminRoleDao;

    /**
     * 判断用户是否存在
     *
     * @param username
     * @return
     */
    public boolean isExist(String username) {
        AdminUser user = adminUserDao.findByUsername(username);
        return null != user;
    }

    /**
     * 判断用户是否存在
     *
     * @param username
     * @return
     */
    public boolean isExist(String username, String mail, String phone) {
        AdminUser user = adminUserDao.findByUsernameOrEmailOrPhone(
                username, mail, phone);
        ;
        return null != user;
    }

    /**
     * 读取用户，不校验密码
     *
     * @param username
     * @return
     */
    public AdminUser getByName(String username) {
        if (log.isDebugEnabled()) {
            log.debug("getByName : username = [{}].", username);
        }
        AdminUser user = adminUserDao.findByUsername(username);
        if (log.isDebugEnabled()) {
            log.debug("getByName : user = [{}].", user);
        }
        if (user == null) {
            return null;
        }
        user = this.loadAdminUserRoles(user);
        if (log.isDebugEnabled()) {
            log.debug("getByName : user = [{}].", user);
        }
        return user;
    }

    /**
     * 读取用户，不校验密码
     *
     * @param username
     * @return
     */
    public AdminUser login(String username, String mail, String phone) {
        if (log.isDebugEnabled()) {
            log.debug("getByName: username = [{}], " +
                            "mail = [{}], phone =[{}].",
                    username, mail, phone);
        }
        AdminUser user = adminUserDao.findByUsernameOrEmailOrPhone(
                username, mail, phone);
        if (user == null) {
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("getByName : user = [{}].", user);
        }
        user = this.loadAdminUserRoles(user);
        if (log.isDebugEnabled()) {
            log.debug("getByName : user = [{}].", user);
        }
        return user;
    }


    /**
     * 读取用户，校验密码
     *
     * @param username
     * @return
     */
    public AdminUser getByNameAndPassword(String username,
                                          String password) {
        if (log.isDebugEnabled()) {
            log.debug("getByName : username = [{}], password = [{}].",
                    username, password);
        }
        AdminUser user = adminUserDao.getByUsernameAndPassword(
                username, password);
        if (user == null) {
            return user;
        }
        user = this.loadAdminUserRoles(user);
        if (log.isDebugEnabled()) {
            log.debug("getByName : user = [{}].", user);
        }
        return user;
    }

    /**
     * 用用户ID读取用户信息，包含角色信息
     *
     * @param uid
     * @return
     */
    public AdminUser getById(Long uid) {
        if (log.isDebugEnabled()) {
            log.debug("getByName : getById = [{}].", uid);
        }
        AdminUser user = adminUserDao.findById(uid).get();
        if (user == null) {
            return user;
        }
        user = this.loadAdminUserRoles(user);
        if (log.isDebugEnabled()) {
            log.debug("getByName : user = [{}].", user);
        }
        return user;
    }

    /**
     * 新用户
     *
     * @param user
     */
    public void add(AdminUser user) {
        adminUserDao.save(user);
    }

    /**
     * 更新数据
     *
     * @param user
     */
    public void addOrUpdate(AdminUser user) {
        adminUserDao.save(user);
    }

    /**
     * 列出全部的用户
     *
     * @return
     */
    public List<AdminUser> listUsers() {
        if (log.isDebugEnabled()) {
            log.debug("listUsers");
        }
        List<AdminUser> users = this.adminUserDao.findAll();
        if (log.isDebugEnabled()) {
            log.debug("listUsers : [{}].", users);
        }
        return users;
    }

    /**
     * 加载用户的全部角色信息
     *
     * @param user
     * @return
     */
    private AdminUser loadAdminUserRoles(AdminUser user) {
        if (log.isDebugEnabled()) {
            log.debug("loadAdminUserRoles ： user = [{}].", user);
        }
        Long uid = user.getId();
        List<AdminUserRole> urs = this.adminUserRoleDao.findDistinctByUidOrderByRid(uid);
        if (log.isDebugEnabled()) {
            log.debug("loadAdminUserRoles ： urs = [{}].", urs);
        }
        if ((urs == null) || (urs.isEmpty())) {
            user.setRoles(new ArrayList<>());
            return user;
        }
        Set<Long> rids = new HashSet<>();
        for (AdminUserRole ur : urs) {
            rids.add(ur.getRid());
        }
        List<AdminRole> roles = this.adminRoleDao.findAllById(rids);
        if (log.isDebugEnabled()) {
            log.debug("loadAdminUserRoles ： roles = [{}].", roles);
        }
        user.setRoles(roles);
        return user;
    }

    /**
     * 删除一个用户
     *
     * @param uid
     */
    public void deleteUser(Long uid) {
        this.adminUserDao.deleteById(uid);
    }
}
