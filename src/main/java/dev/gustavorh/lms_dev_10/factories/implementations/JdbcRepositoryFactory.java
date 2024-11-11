package dev.gustavorh.lms_dev_10.factories.implementations;

import dev.gustavorh.lms_dev_10.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IAuthorRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IBookRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.ICategoryRepository;
import dev.gustavorh.lms_dev_10.repositories.implementations.JdbcAuthorRepository;
import dev.gustavorh.lms_dev_10.repositories.implementations.JdbcBookRepository;
import dev.gustavorh.lms_dev_10.repositories.implementations.JdbcCategoryRepository;

import java.sql.Connection;

public class JdbcRepositoryFactory implements IRepositoryFactory {
    private Connection connection;

    public JdbcRepositoryFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public IBookRepository createBookRepository() {
        return new JdbcBookRepository(connection);
    }

    @Override
    public IAuthorRepository createAuthorRepository() {
        return new JdbcAuthorRepository(connection);
    }

    @Override
    public ICategoryRepository createCategoryRepository() {
        return new JdbcCategoryRepository(connection);
    }
}
