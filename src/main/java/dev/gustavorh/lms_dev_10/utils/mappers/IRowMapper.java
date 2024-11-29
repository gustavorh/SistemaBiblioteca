package dev.gustavorh.lms_dev_10.utils.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Las implementaciones de esta interfaz son responsables de
 * mapear los resultados de una consulta (ResultSet) a un objeto (POJO).
 * @param <T>
 */
public interface IRowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}
