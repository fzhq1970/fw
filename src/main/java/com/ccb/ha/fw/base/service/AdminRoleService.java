package com.ccb.ha.fw.base.service;

import com.ccb.ha.fw.base.dao.*;
import com.ccb.ha.fw.base.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminRoleService {
    private static final Logger log =
            LoggerFactory.getLogger(AdminRoleService.class);
    @Autowired
    AdminRoleDao adminRoleDao;
    @Autowired
    AdminRoleMenuDao adminRoleMenuDao;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    AdminRolePermissionDao adminRolePermissionDao;
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    AdminUserRoleDao adminUserRoleDao;


    /**
     * 用角色代码获取角色
     *
     * @param id
     * @return
     */
    public AdminRole findById(Long id) {
        return adminRoleDao.findById(id).get();
    }

    /**
     * 读取用户
     *
     * @param roleName
     * @return
     */
    public AdminRole getByName(String roleName) {
        return adminRoleDao.findByName(roleName);
    }

    /**
     * 新角色
     *
     * @param role
     */
    public void addOrUpdate(AdminRole role) {
        this.adminRoleDao.save(role);
    }

    /**
     * 列出全部的用户
     *
     * @return
     */
    public List<AdminRole> listRoles() {
        if (log.isDebugEnabled()) {
            log.debug("listRoles");
        }
        List<AdminRole> roles = this.adminRoleDao.findAll();
        if (log.isDebugEnabled()) {
            log.debug("listRoles : [{}].", roles);
        }
        return roles;
    }

    public List<AdminRole> list() {
        List<AdminRole> roles = this.adminRoleDao.findAll();
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles) {
            perms = adminPermissionService.listPermsByRoleId(role.getId());
            menus = adminMenuService.getMenusByRoleId(role.getId());
            role.setPerms(perms);
            role.setMenus(menus);
        }
        return roles;
    }

    /**
     * 更新一个角色信息及其权限信息
     */
    @Transactional
    public void savePermChanges(AdminRole requestRole) {
        if (log.isDebugEnabled()) {
            log.debug("savePermChanges requestRole : [{}].", requestRole);
        }
        //更细角色信息
        this.addOrUpdate(requestRole);
        Long rid = requestRole.getId();
        //先清理原有的
        adminRolePermissionDao.deleteAllByRid(rid);
        AdminRolePermission rp;
        for (AdminPermission perm : requestRole.getPerms()) {
            rp = new AdminRolePermission();
            rp.setPid(perm.getId());
            rp.setRid(rid);
            rp.setId(null);
            this.adminRolePermissionDao.save(rp);
        }
    }

    /**
     * @param rid
     * @param menus
     */
    public void updateRoleMenu(Long rid, Iterable<AdminRoleMenu> menus) {
        this.adminRoleMenuDao.deleteAllByRid(rid);
        for (AdminRoleMenu rm : menus) {
            this.adminRoleMenuDao.save(rm);
        }
    }

    /**
     * 获取用户的角色信息
     *
     * @param username
     * @return
     */
    public List<AdminRole> listRolesByUser(String username) {
        if (log.isDebugEnabled()) {
            log.debug("listRolesByUser ： [{}].", username);
        }
        AdminUser user = this.adminUserDao.findByUsername(username);
        if (user == null) {
            if (log.isDebugEnabled()) {
                log.debug("listRolesByUser not found ： [{}].", username);
            }
            //用户名称有误
            List<AdminRole> roles = new ArrayList<>();
            return roles;
        }
        Long uid = this.adminUserDao.findByUsername(username).getId();
        List<AdminUserRole> urs = adminUserRoleDao.findDistinctByUidOrderByRid(uid);
        Set<Long> rids = new HashSet<>();
        for (AdminUserRole ur : urs) {
           rids.add(ur.getRid());
        }
        List<AdminRole> roles = this.adminRoleDao.findAllById(rids);
        if (log.isDebugEnabled()) {
            log.debug("listRolesByUser roles ： [{}].", roles);
        }
        return roles;
    }
}
