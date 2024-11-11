package dev.gustavorh.lms_dev_10.factories.interfaces;

import dev.gustavorh.lms_dev_10.services.implementations.AuthorService;
import dev.gustavorh.lms_dev_10.services.implementations.BookService;
import dev.gustavorh.lms_dev_10.services.implementations.CategoryService;

public interface IServiceFactory {
    BookService createBookService();
    AuthorService createAuthorService();
    CategoryService createCategoryService();
}
