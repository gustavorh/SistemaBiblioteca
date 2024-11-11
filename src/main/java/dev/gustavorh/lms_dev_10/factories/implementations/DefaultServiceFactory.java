package dev.gustavorh.lms_dev_10.factories.implementations;

import dev.gustavorh.lms_dev_10.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IServiceFactory;
import dev.gustavorh.lms_dev_10.services.implementations.AuthorService;
import dev.gustavorh.lms_dev_10.services.implementations.BookService;
import dev.gustavorh.lms_dev_10.services.implementations.CategoryService;

public class DefaultServiceFactory implements IServiceFactory {
    private final IRepositoryFactory repositoryFactory;

    public DefaultServiceFactory(IRepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public BookService createBookService() {
        return new BookService(repositoryFactory.createBookRepository());
    }

    @Override
    public AuthorService createAuthorService() {
        return new AuthorService(repositoryFactory.createAuthorRepository());
    }

    @Override
    public CategoryService createCategoryService() {
        return new CategoryService(repositoryFactory.createCategoryRepository());
    }
}
