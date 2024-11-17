package dev.gustavorh.lms_dev_10.services.implementations;

import dev.gustavorh.lms_dev_10.entities.Book;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.services.interfaces.IService;
import dev.gustavorh.lms_dev_10.utils.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class BookService implements IService<Book> {
    private final IRepository<Book> bookRepository;

    public BookService(IRepository<Book> bookRepository) {
        this.bookRepository = bookRepository;
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
