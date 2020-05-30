package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.AdminRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRoleMenuDao extends JpaRepository<AdminRoleMenu, Long> {
    List<AdminRoleMenu> findDistinctByRidIsInOrderByMid(Iterable<Long> rids);
    void deleteAllByRid(Long rid);
}
