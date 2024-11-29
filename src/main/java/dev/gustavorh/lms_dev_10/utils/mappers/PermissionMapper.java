package dev.gustavorh.lms_dev_10.utils.mappers;

import dev.gustavorh.lms_dev_10.domain.entities.Permission;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionMapper implements IRowMapper<Permission> {
    @Override
    public Permission mapRow(ResultSet rs) throws SQLException {
        Permission permission = new Permission();
        permission.setPermissionId(rs.getLong("id_permiso"));
        permission.setName(rs.getString("nombre"));
        permission.setDescription(rs.getString("descripcion"));
        return permission;
    }
}
