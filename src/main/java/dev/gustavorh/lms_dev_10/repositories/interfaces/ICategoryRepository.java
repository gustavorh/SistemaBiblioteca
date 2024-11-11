package dev.gustavorh.lms_dev_10.repositories.interfaces;

import dev.gustavorh.lms_dev_10.entities.Category;

import java.sql.SQLException;

public interface ICategoryRepository extends IRepository<Category> {
    Category findByName(String name) throws SQLException;
}
