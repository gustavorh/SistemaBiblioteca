package dev.gustavorh.lms_dev_10.application.services.implementations;

import dev.gustavorh.lms_dev_10.application.security.PasswordEncoder;
import dev.gustavorh.lms_dev_10.domain.entities.User;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IAuthService;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class AuthService implements IAuthService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        return loggedIn != null && loggedIn;
    }

    @Override
    public Optional<User> login(String userName, String password) {
        return Optional.ofNullable(userService.findByUserName(userName))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
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
