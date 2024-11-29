package dev.gustavorh.lms_dev_10.infrastructure.repositories.interfaces;

import dev.gustavorh.lms_dev_10.domain.entities.User;

import java.sql.SQLException;

public interface IUserRepository extends IRepository<User> {
    User findByUserName(String userName) throws SQLException;
    void updateRole(Long userId, Long roleId) throws SQLException;
}
