package dev.gustavorh.lms_dev_10.services.implementations;

import dev.gustavorh.lms_dev_10.entities.Author;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IAuthorRepository;
import dev.gustavorh.lms_dev_10.services.interfaces.IAuthorService;
import dev.gustavorh.lms_dev_10.utils.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class AuthorService implements IAuthorService {
    private final IAuthorRepository authorRepository;

    public AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findByNameLike(String name) {
        try {
            return authorRepository.findByNameLike(name);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving authors by author with name: " + name, e);
        }
    }

    @Override
    public Author findById(Long id) {
        try {
            return authorRepository.findById(id);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving author with id: " + id, e);
        }
    }

    @Override
    public List<Author> findAll() {
        try {
            return authorRepository.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving all authors", e);
        }
    }

    @Override
    public void save(Author entity) {
        try {
            authorRepository.save(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error saving author", e);
        }
    }

    @Override
    public void update(Author entity) {
        try {
            authorRepository.update(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error updating author", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            authorRepository.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("Error deleting author with id: " + id, e);
        }
    }
}
