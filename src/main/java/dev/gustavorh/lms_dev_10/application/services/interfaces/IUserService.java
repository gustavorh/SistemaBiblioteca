package dev.gustavorh.lms_dev_10.application.services.interfaces;

import dev.gustavorh.lms_dev_10.domain.entities.User;

/**
 * Esta interfaz brinda implementaciones para la lógica de negocio.
 */
public interface IUserService extends IService<User> {
    User findByUserName(String userName);
    void assignRole(Long userId, Long roleId);
}
