package com.ccb.ha.fw.base.controller;

import com.ccb.ha.fw.base.dao.CategoryDao;
import com.ccb.ha.fw.base.entity.Book;
import com.ccb.ha.fw.base.entity.Category;
import com.ccb.ha.fw.base.exception.BaseException;
import com.ccb.ha.fw.base.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/library")
public class LibraryController extends BaseController {
    private static final Logger log =
            LoggerFactory.getLogger(LibraryController.class);
    @Autowired
    BookService bookService;
    @Autowired
    CategoryDao categoryDAO;

    @GetMapping("/books")
    public List<Book> list() throws BaseException {
        return bookService.list();
    }

    @PostMapping("/books")
    public Book addOrUpdate(@RequestBody Book book) throws BaseException {
        if (log.isDebugEnabled()) {
            log.debug("save or update book ： [{}].", book);
        }
        bookService.addOrUpdate(book);
        if (log.isDebugEnabled()) {
            log.debug("save or update book ： [{}].", book);
        }
        return book;
    }

    @PostMapping("/book/delete")
    public void delete(@RequestBody Book book) throws BaseException {
        bookService.deleteById(book.getId());
    }

    @GetMapping("/categories/{cid}/books")
    public List<Book> listByCategory(@PathVariable("cid") Long cid) throws BaseException {
        if (0 != cid) {
            return bookService.listByCategory(cid);
        } else {
            return list();
        }
    }

    @RequestMapping("/categories")
    public List<Category> categories() throws BaseException {
        return categoryDAO.findAll();
    }

    @RequestMapping("/category/{id}")
    public Category addOrUpdate(@PathVariable("id") Long id) throws BaseException {
        return this.categoryDAO.getOne(id);
    }
}
