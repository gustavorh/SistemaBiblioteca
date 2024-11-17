package dev.gustavorh.lms_dev_10.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface ILoginService {
    Optional<String> getUsername(HttpServletRequest request);
}
