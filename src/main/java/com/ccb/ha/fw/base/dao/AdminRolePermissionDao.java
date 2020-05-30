package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.AdminRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRolePermissionDao  extends JpaRepository<AdminRolePermission, Long> {
    List<AdminRolePermission> findAdminRolePermissionByRidOrderByPid(Long rid);
    void deleteAllByRid(Long rid);
}
