package dev.gustavorh.lms_dev_10.services.interfaces;

import dev.gustavorh.lms_dev_10.entities.Category;

public interface ICategoryService extends IService<Category> {
    Category findByName(String name);
}
