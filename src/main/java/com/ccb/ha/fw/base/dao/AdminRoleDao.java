package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleDao extends JpaRepository<AdminRole, Long> {
    AdminRole findByName(String roleName);
}
