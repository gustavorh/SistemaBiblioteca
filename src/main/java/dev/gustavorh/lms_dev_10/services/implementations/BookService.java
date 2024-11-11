package dev.gustavorh.lms_dev_10.services.implementations;

import dev.gustavorh.lms_dev_10.entities.Book;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IBookRepository;
import dev.gustavorh.lms_dev_10.services.interfaces.IBookService;
import dev.gustavorh.lms_dev_10.utils.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class BookService implements IBookService {
    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findByAuthor(Long authorId) {
        try {
            return bookRepository.findByAuthor(authorId);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving books by author with id: " + authorId, e);
        }
    }

    @Override
    public List<Book> findByCategory(Long categoryId) {
        try {
            return bookRepository.findByCategory(categoryId);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving books by category with id: " + categoryId, e);
        }
    }

    @Override
    public Book findById(Long id) {
        try {
            return bookRepository.findById(id);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return bookRepository.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving all books", e);
        }
    }

    @Override
    public void save(Book entity) {
        try {
            bookRepository.save(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error saving book", e);
        }
    }

    @Override
    public void update(Book entity) {
        try {
            bookRepository.update(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error updating book", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            bookRepository.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("Error deleting book with id: " + id, e);
        }
    }
}
