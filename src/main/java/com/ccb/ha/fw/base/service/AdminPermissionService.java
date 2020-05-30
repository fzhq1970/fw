package com.ccb.ha.fw.base.service;

import com.ccb.ha.fw.base.dao.*;
import com.ccb.ha.fw.base.entity.AdminPermission;
import com.ccb.ha.fw.base.entity.AdminRole;
import com.ccb.ha.fw.base.entity.AdminRolePermission;
import com.ccb.ha.fw.base.entity.AdminUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminPermissionService {
    private static final Logger log =
            LoggerFactory.getLogger(AdminPermissionService.class);
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    AdminUserRoleDao adminUserRoleDaO;
    @Autowired
    AdminRoleDao adminRoleDaO;
    @Autowired
    AdminPermissionDao adminPermissionDao;
    @Autowired
    AdminRolePermissionDao adminRolePermissionDao;

    /**
     * @param username
     * @return
     */
    public Set<String> listPermissionURLsByUser(String username) {
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : [{}].", username);
        }
        Long uid = adminUserDao.findByUsername(username).getId();
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : uid = [{}].", uid);
        }

        List<AdminUserRole> urs = this.adminUserRoleDaO.
                findDistinctByUidOrderByRid(uid);
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : urs = [{}].", urs);
        }

        Set<Long> ids = new HashSet<>();
        for (AdminUserRole ur : urs) {
            ids.add(ur.getRid());
        }

        List<AdminRole> roles = this.adminRoleDaO.findAllById(ids);
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : roles = [{}].", roles);
        }
        //所有的授权信息代码
        Set<Long> pids = new HashSet<>();
        for (Long rid : ids) {
            List<AdminRolePermission> rps = this.adminRolePermissionDao.
                    findAdminRolePermissionByRidOrderByPid(rid);
            if (log.isDebugEnabled()) {
                log.debug("listPermissionURLsByUser : rps = [{}].", rps);
            }
            for (AdminRolePermission rp : rps) {
                pids.add(rp.getPid());
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : pids = [{}].", pids);
        }

        Set<String> urls = new HashSet<>();
        //所有权限信息
        List<AdminPermission> perms = this.adminPermissionDao.findAllById(pids);
        for (AdminPermission perm : perms) {
            urls.add(perm.getUrl());
        }
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : urls = [{}].", urls);
        }
        return urls;
    }

    /**
     * 通过角色ID获取该角色的全部授权
     *
     * @param rid
     * @return
     */
    public List<AdminPermission> listPermsByRoleId(Long rid) {
        if (log.isDebugEnabled()) {
            log.debug("listPermsByRoleId : rid = [{}].", rid);
        }
        Set<Long> ids = new HashSet<>();
        List<AdminRolePermission> rps = this.adminRolePermissionDao.
                findAdminRolePermissionByRidOrderByPid(rid);
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : rps = [{}].", rps);
        }
        for (AdminRolePermission rp : rps) {
            ids.add(rp.getPid());
        }
        List<AdminPermission> ps = this.adminPermissionDao.findDistinctByIdIn(ids);
        if (log.isDebugEnabled()) {
            log.debug("listPermissionURLsByUser : ps = [{}].", ps);
        }
        return ps;
    }

    /**
     * @param requestAPI
     * @return
     */
    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = this.adminPermissionDao.findAll();
        for (AdminPermission p : ps) {
            if (p.getUrl().equals(requestAPI)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 列出所有授权信息
     *
     * @return
     */
    public List<AdminPermission> listAllPerms() {
        List<AdminPermission> ps = this.adminPermissionDao.findAll();
        if (log.isDebugEnabled()) {
            log.debug("listAllPerms : [{}].", ps);
        }
        return ps;
    }
}
