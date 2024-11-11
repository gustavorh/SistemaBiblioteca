package dev.gustavorh.lms_dev_10.services.interfaces;

import dev.gustavorh.lms_dev_10.entities.Author;

import java.util.List;

public interface IAuthorService extends IService<Author> {
    List<Author> findByNameLike(String name);
}
