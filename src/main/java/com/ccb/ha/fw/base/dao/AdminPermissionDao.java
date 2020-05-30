package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Iterator;
import java.util.List;

public interface AdminPermissionDao extends JpaRepository<AdminPermission, Long> {
    /**
     * 获取全部的列表中的权限信息
     * @param ids
     * @return
     */
    List<AdminPermission> findDistinctByIdIn(Iterable<Long> ids);
}
