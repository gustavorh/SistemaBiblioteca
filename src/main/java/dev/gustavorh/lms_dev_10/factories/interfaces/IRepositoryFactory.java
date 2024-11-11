package dev.gustavorh.lms_dev_10.factories.interfaces;

import dev.gustavorh.lms_dev_10.repositories.interfaces.IAuthorRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IBookRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.ICategoryRepository;

public interface IRepositoryFactory {
    IBookRepository createBookRepository();
    IAuthorRepository createAuthorRepository();
    ICategoryRepository createCategoryRepository();
}
