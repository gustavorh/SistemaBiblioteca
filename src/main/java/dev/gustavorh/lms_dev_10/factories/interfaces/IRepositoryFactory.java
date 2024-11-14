package dev.gustavorh.lms_dev_10.factories.interfaces;

import dev.gustavorh.lms_dev_10.entities.Author;
import dev.gustavorh.lms_dev_10.entities.Book;
import dev.gustavorh.lms_dev_10.entities.Category;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IRepository;

public interface IRepositoryFactory {
    IRepository<Book> createBookRepository();
    IRepository<Author> createAuthorRepository();
    IRepository<Category> createCategoryRepository();
}
