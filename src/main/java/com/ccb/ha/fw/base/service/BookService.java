package com.ccb.ha.fw.base.service;

import com.ccb.ha.fw.base.dao.BookDao;
import com.ccb.ha.fw.base.entity.Book;
import com.ccb.ha.fw.base.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDao bookDAO;
    @Autowired
    CategoryService categoryService;

    public List<Book> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return bookDAO.findAll(sort);
    }

    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }

    public void deleteById(Long id) {
        bookDAO.deleteById(id);
    }

    public List<Book> listByCategory(Long cid) {
        Category category = categoryService.get(cid);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return bookDAO.findAllByCategory(category);
    }
}