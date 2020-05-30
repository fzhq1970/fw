package com.ccb.ha.fw.base.service;

import com.ccb.ha.fw.base.dao.AdminUserRoleDao;
import com.ccb.ha.fw.base.entity.AdminRole;
import com.ccb.ha.fw.base.entity.AdminUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserRoleService {

    private static final Logger log =
            LoggerFactory.getLogger(AdminUserRoleService.class);

    @Autowired
    AdminUserRoleDao adminUserRoleDao;

    /**
     * @param uid
     */
    public void saveRoleChanges(Long uid, Iterable<AdminRole> roles) {
        if (log.isDebugEnabled()) {
            log.debug("saveRoleChanges : uid = [{}], roles =[{}].",
                    uid, roles);
        }
        if (roles == null) {
            log.info("saveRoleChanges : uid is null.");
            return;
        }
        //删除已有数据
        this.adminUserRoleDao.deleteAllByUid(uid);
        List<AdminUserRole> urs = new ArrayList<>();
        for (AdminRole role : roles) {
            AdminUserRole ur = new AdminUserRole(uid, role.getId());
            if (log.isDebugEnabled()) {
                log.debug("saveRoleChanges : ur =  [{}].", ur);
            }
            urs.add(ur);
        }
        this.adminUserRoleDao.saveAll(urs);
        if (log.isDebugEnabled()) {
            log.debug("saveRoleChanges : urs =  [{}].", urs);
        }
    }

    /**
     * 删除一个用户的全部角色
     *
     * @param uid
     */
    public void deleteUserRoles(Long uid) {
        if (log.isDebugEnabled()) {
            log.debug("deleteUserRoles : uid =[{}].", uid);
        }
        this.adminUserRoleDao.deleteAllByUid(uid);
    }
}
