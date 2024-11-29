package dev.gustavorh.lms_dev_10.application.services.interfaces;

import dev.gustavorh.lms_dev_10.domain.entities.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * Esta interfaz brinda implementaciones para la lógica de negocio.
 */
public interface IAuthService {
    Boolean isLoggedIn(HttpServletRequest request);
    Optional<String> getUsername(HttpServletRequest request);
    Optional<User> login(String userName, String password);
}
