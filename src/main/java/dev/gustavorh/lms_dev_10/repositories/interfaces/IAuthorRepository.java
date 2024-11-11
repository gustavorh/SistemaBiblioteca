package dev.gustavorh.lms_dev_10.repositories.interfaces;

import dev.gustavorh.lms_dev_10.entities.Author;

import java.sql.SQLException;
import java.util.List;

public interface IAuthorRepository extends IRepository<Author> {
    List<Author> findByNameLike(String name) throws SQLException;
}
