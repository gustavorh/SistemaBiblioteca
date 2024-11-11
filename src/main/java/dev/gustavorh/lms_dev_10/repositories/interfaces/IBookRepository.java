package dev.gustavorh.lms_dev_10.repositories.interfaces;

import dev.gustavorh.lms_dev_10.entities.Book;

import java.sql.SQLException;
import java.util.List;

public interface IBookRepository extends IRepository<Book> {
    List<Book> findByAuthor(Long authorId) throws SQLException;
    List<Book> findByCategory(Long categoryId) throws SQLException;
}
