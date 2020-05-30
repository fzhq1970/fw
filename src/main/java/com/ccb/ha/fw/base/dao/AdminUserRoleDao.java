package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRoleDao extends JpaRepository<AdminUserRole, Long> {

    /**
     * 根据用户代码读取全部的角色信息
     *
     * @param uid
     * @return
     */
    List<AdminUserRole> findDistinctByUidOrderByRid(Long uid);

    /**
     * 删除一个用户的角色信息
     * @param uid
     */
    void deleteAllByUid(Long uid);

}
