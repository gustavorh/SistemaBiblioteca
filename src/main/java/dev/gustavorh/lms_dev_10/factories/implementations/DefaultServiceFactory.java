package dev.gustavorh.lms_dev_10.factories.implementations;

import dev.gustavorh.lms_dev_10.entities.Author;
import dev.gustavorh.lms_dev_10.entities.Book;
import dev.gustavorh.lms_dev_10.entities.Category;
import dev.gustavorh.lms_dev_10.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IServiceFactory;
import dev.gustavorh.lms_dev_10.services.implementations.AuthService;
import dev.gustavorh.lms_dev_10.services.implementations.AuthorService;
import dev.gustavorh.lms_dev_10.services.implementations.BookService;
import dev.gustavorh.lms_dev_10.services.implementations.CategoryService;
import dev.gustavorh.lms_dev_10.services.implementations.UserService;
import dev.gustavorh.lms_dev_10.services.interfaces.IAuthService;
import dev.gustavorh.lms_dev_10.services.interfaces.IService;
import dev.gustavorh.lms_dev_10.services.interfaces.IUserService;

public class DefaultServiceFactory implements IServiceFactory {
    private final IRepositoryFactory repositoryFactory;

    public DefaultServiceFactory(IRepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }


    @Override
    public IAuthService createAuthService() {
        return new AuthService(repositoryFactory.createUserRepository());
    }

    @Override
    public IUserService createUserService() {
        return new UserService(repositoryFactory.createUserRepository());
    }

    @Override
    public IService<Book> createBookService() {
        return new BookService(repositoryFactory.createBookRepository());
    }

    @Override
    public IService<Author> createAuthorService() {
        return new AuthorService(repositoryFactory.createAuthorRepository());
    }

    @Override
    public IService<Category> createCategoryService() {
        return new CategoryService(repositoryFactory.createCategoryRepository());
    }
}
