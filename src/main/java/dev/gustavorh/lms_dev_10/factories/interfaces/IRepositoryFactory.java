package dev.gustavorh.lms_dev_10.factories.interfaces;

import dev.gustavorh.lms_dev_10.entities.Author;
import dev.gustavorh.lms_dev_10.entities.Book;
import dev.gustavorh.lms_dev_10.entities.Category;
import dev.gustavorh.lms_dev_10.repositories.implementations.MemberActivityReportRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IUserRepository;

public interface IRepositoryFactory {
    IUserRepository createUserRepository();
    IRepository<Book> createBookRepository();
    IRepository<Author> createAuthorRepository();
    IRepository<Category> createCategoryRepository();
    MemberActivityReportRepository createMemberActivityReportRepository();
}
