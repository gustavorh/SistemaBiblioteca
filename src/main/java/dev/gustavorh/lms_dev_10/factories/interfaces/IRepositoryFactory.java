package dev.gustavorh.lms_dev_10.factories.interfaces;

import dev.gustavorh.lms_dev_10.repositories.implementations.MemberActivityReportRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IAuthorRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IBookRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.ICategoryRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IUserRepository;

public interface IRepositoryFactory {
    IUserRepository createUserRepository();
    IBookRepository createBookRepository();
    IAuthorRepository createAuthorRepository();
    ICategoryRepository createCategoryRepository();
    MemberActivityReportRepository createMemberActivityReportRepository();
}
