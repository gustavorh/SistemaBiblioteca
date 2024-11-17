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

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private IUserService userService;
    private ILoginService loginService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Connection connection = DbContext.getConnection();
            IRepositoryFactory repositoryFactory = new JdbcRepositoryFactory(connection);
            IServiceFactory serviceFactory = new DefaultServiceFactory(repositoryFactory);

            userService = serviceFactory.createUserService();
            loginService = serviceFactory.createLoginService();
        } catch (SQLException e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> usernameOptional = loginService.getUsername(req);

        if (usernameOptional.isPresent()) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<User> userOptional = userService.login(username, password);

        if (userOptional.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/books/ver");
        } else {
            req.setAttribute("title", "Login incorrecto!");
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }
}
