package dev.gustavorh.lms_dev_10.services.implementations;

import dev.gustavorh.lms_dev_10.entities.User;
import dev.gustavorh.lms_dev_10.repositories.interfaces.IUserRepository;
import dev.gustavorh.lms_dev_10.services.interfaces.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService implements IAuthService {
    private final IUserRepository userRepository;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

        return loggedIn != null && loggedIn;
    }

    @Override
    public Optional<User> login(String userName, String password){
        try {
            return Optional.ofNullable(userRepository.findByUserName(userName))
                    .filter(user -> user.getPassword().equals(password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

        if (loggedIn != null && loggedIn) {
            return Optional.of((String) session.getAttribute("username"));
        }

        return Optional.empty();
    }
}
