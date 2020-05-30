package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMenuDao extends JpaRepository<AdminMenu, Long> {

    /**
     * 读取一批菜单信息
     * @param ids
     * @return
     */
    List<AdminMenu> findDistinctByIdIn(Iterable<Long> ids);
}
