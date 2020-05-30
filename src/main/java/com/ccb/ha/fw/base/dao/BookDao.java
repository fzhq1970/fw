package com.ccb.ha.fw.base.dao;

import com.ccb.ha.fw.base.entity.Book;
import com.ccb.ha.fw.base.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book,Long> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}
