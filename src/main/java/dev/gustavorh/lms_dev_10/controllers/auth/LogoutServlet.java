package dev.gustavorh.lms_dev_10.controllers.auth;

import dev.gustavorh.lms_dev_10.config.DbContext;
import dev.gustavorh.lms_dev_10.factories.implementations.DefaultServiceFactory;
import dev.gustavorh.lms_dev_10.factories.implementations.JdbcRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IServiceFactory;
import dev.gustavorh.lms_dev_10.services.interfaces.ILoginService;
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

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {
    private ILoginService loginService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Connection connection = DbContext.getConnection();
            IRepositoryFactory repositoryFactory = new JdbcRepositoryFactory(connection);
            IServiceFactory serviceFactory = new DefaultServiceFactory(repositoryFactory);

            loginService = serviceFactory.createLoginService();
        } catch (SQLException e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> username = loginService.getUsername(req);
        if (username.isPresent()) {
            HttpSession session = req.getSession();
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/auth/login");
    }
}
