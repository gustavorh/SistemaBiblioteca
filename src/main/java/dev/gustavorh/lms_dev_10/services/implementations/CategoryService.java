package dev.gustavorh.lms_dev_10.services.implementations;

import dev.gustavorh.lms_dev_10.entities.Category;
import dev.gustavorh.lms_dev_10.repositories.interfaces.ICategoryRepository;
import dev.gustavorh.lms_dev_10.services.interfaces.ICategoryService;
import dev.gustavorh.lms_dev_10.utils.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findByName(String name) {
        try {
            return categoryRepository.findByName(name);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving categories by author with name: " + name, e);
        }
    }

    @Override
    public Category findById(Long id) {
        try {
            return categoryRepository.findById(id);
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving category with id: " + id, e);
        }
    }

    @Override
    public List<Category> findAll() {
        try {
            return categoryRepository.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Error retrieving all categories", e);
        }
    }

    @Override
    public void save(Category entity) {
        try {
            categoryRepository.save(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error saving category", e);
        }
    }

    @Override
    public void update(Category entity) {
        try {
            categoryRepository.update(entity);
        } catch (SQLException e) {
            throw new ServiceException("Error updating category", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            categoryRepository.delete(id);
        } catch (SQLException e) {
            throw new ServiceException("Error deleting category with id: " + id, e);
        }
    }
}
