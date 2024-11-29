package dev.gustavorh.lms_dev_10.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Esta interfaz genérica proporciona los métodos básicos para
 * poder trabajar con operaciones CRUD en la base de datos.
 * <p>
 * Las implementaciones de estos servicios interactúan directamente
 * con la capa de dominio (Modelo en una arquitectura MVC).
 * @param <T>
 */
public interface IRepository<T> {
    Optional<T> findById(Long id) throws SQLException;
    List<T> findAll() throws SQLException;
    void save(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(Long id) throws SQLException;
}
