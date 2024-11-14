package dev.gustavorh.lms_dev_10.services.interfaces;

import java.util.List;

// Generic Service interfaces
public interface IService<T> {
    T findById(Long id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(Long id);
}
