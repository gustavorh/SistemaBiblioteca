package dev.gustavorh.lms_dev_10.factories.implementations;

import dev.gustavorh.lms_dev_10.entities.Author;
import dev.gustavorh.lms_dev_10.entities.Book;
import dev.gustavorh.lms_dev_10.entities.Category;
import dev.gustavorh.lms_dev_10.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.repositories.implementations.JdbcAuthorRepository;
import dev.gustavorh.lms_dev_10.repositories.implementations.JdbcBookRepository;
import dev.gustavorh.lms_dev_10.repositories.implementations.JdbcCategoryRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IRepository;

import java.sql.Connection;

public class JdbcRepositoryFactory implements IRepositoryFactory {
    private final Connection connection;

    public JdbcRepositoryFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public IRepository<Book> createBookRepository() {
        return new JdbcBookRepository(connection);
    }

    @Override
    public IRepository<Author> createAuthorRepository() {
        return new JdbcAuthorRepository(connection);
    }

    @Override
    public IRepository<Category> createCategoryRepository() {
        return new JdbcCategoryRepository(connection);
    }
}
