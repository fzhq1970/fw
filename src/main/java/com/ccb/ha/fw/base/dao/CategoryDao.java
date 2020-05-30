package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
