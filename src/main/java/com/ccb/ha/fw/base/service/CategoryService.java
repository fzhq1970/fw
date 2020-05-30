package com.ccb.ha.fw.base.service;

import com.ccb.ha.fw.base.dao.CategoryDao;
import com.ccb.ha.fw.base.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDao categoryDAO;

    public List<Category> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    public Category get(Long id) {
        Category c = categoryDAO.findById(id).orElse(null);
        return c;
    }
}