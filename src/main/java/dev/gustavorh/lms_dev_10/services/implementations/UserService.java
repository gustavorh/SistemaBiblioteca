package dev.gustavorh.lms_dev_10.services.implementations;

import dev.gustavorh.lms_dev_10.entities.User;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IRepository;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IUserRepository;
import dev.gustavorh.lms_dev_10.services.interfaces.IUserService;

import java.sql.SQLException;
import java.util.Optional;

public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String userName, String password) {
        try {
            return Optional.ofNullable(userRepository.findByUserName(userName))
                    .filter(user -> user.getPassword().equals(password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
