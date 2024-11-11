package dev.gustavorh.lms_dev_10.services.interfaces;

import dev.gustavorh.lms_dev_10.entities.Book;

import java.util.List;

public interface IBookService extends IService<Book> {
    List<Book> findByAuthor(Long authorId);
    List<Book> findByCategory(Long categoryId);
}
