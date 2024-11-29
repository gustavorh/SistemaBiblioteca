package dev.gustavorh.lms_dev_10.services.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Esta interfaz genérica proporciona los métodos básicos para
 * poder trabajar con operaciones CRUD en la base de datos.
 * <p>
 * Las implementaciones de estos servicios interactúan directamente
 * con la capa de acceso a datos.
 * @param <T>
 */
public interface IService<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(Long id);
}
