package dev.gustavorh.lms_dev_10.factories.interfaces;

import dev.gustavorh.lms_dev_10.services.implementations.AuthorService;
import dev.gustavorh.lms_dev_10.services.implementations.BookService;
import dev.gustavorh.lms_dev_10.services.implementations.CategoryService;
import dev.gustavorh.lms_dev_10.services.implementations.LoginService;
import dev.gustavorh.lms_dev_10.services.implementations.UserService;

public interface IServiceFactory {
    LoginService createLoginService();
    UserService createUserService();
    BookService createBookService();
    AuthorService createAuthorService();
    CategoryService createCategoryService();
}
