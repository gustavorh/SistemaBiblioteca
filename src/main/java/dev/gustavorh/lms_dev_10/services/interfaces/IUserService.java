package dev.gustavorh.lms_dev_10.services.interfaces;

import dev.gustavorh.lms_dev_10.entities.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> login(String userName, String password);
}
