package dev.gustavorh.lms_dev_10.controllers.auth;

import dev.gustavorh.lms_dev_10.config.DbContext;
import dev.gustavorh.lms_dev_10.entities.User;
import dev.gustavorh.lms_dev_10.factories.implementations.DefaultServiceFactory;
import dev.gustavorh.lms_dev_10.factories.implementations.JdbcRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IServiceFactory;
import dev.gustavorh.lms_dev_10.services.interfaces.ILoginService;
import dev.gustavorh.lms_dev_10.services.interfaces.IUserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }
}
