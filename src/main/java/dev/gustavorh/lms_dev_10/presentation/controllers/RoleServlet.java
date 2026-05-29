package dev.gustavorh.lms_dev_10.presentation.controllers;

import dev.gustavorh.lms_dev_10.infrastructure.config.DbContext;
import dev.gustavorh.lms_dev_10.domain.entities.Role;
import dev.gustavorh.lms_dev_10.application.factories.implementations.DefaultServiceFactory;
import dev.gustavorh.lms_dev_10.application.factories.implementations.JdbcRepositoryFactory;
import dev.gustavorh.lms_dev_10.application.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.application.factories.interfaces.IServiceFactory;
import dev.gustavorh.lms_dev_10.application.services.interfaces.IService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/roles","/roles/*"})
public class RoleServlet extends HttpServlet {
    private IService<Role> roleService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbContext.getConnection();
            IRepositoryFactory repositoryFactory = new JdbcRepositoryFactory(connection);
            IServiceFactory serviceFactory = new DefaultServiceFactory(repositoryFactory);

            roleService = serviceFactory.createRoleService();
        } catch (SQLException e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null) path = "/";

        switch (path) {
            case "/", "/all":
                request.setAttribute("roles", roleService.findAll());
                request.getRequestDispatcher("/WEB-INF/views/roles/roles.jsp").forward(request, response);
                break;
            case "/create":
                request.setAttribute("role", new Role()); // Empty book for the form
                request.setAttribute("action", "create");
                request.getRequestDispatcher("/WEB-INF/views/roles/form-role.jsp").forward(request, response);
                break;
            case "/edit":
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                request.setAttribute("role", roleService.findById(Long.valueOf(request.getParameter("id"))).get());
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("/WEB-INF/views/roles/form-role.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null) { response.sendError(HttpServletResponse.SC_NOT_FOUND); return; }
        try {
            if (path.equals("/create")) {
                // Populate book object from form parameters
                Role role = buildRole(request, response);

                // Validate the book data
                Map<String, String> errors = validateRole(role);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("role", role);
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("/WEB-INF/views/roles/form-role.jsp")
                            .forward(request, response);
                    return;
                }
                roleService.save(role);
                // Redirect to book list
                response.sendRedirect(request.getContextPath() + "/roles/all");
            } else if (path.equals("/edit")) {
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Role role = buildRole(request, response);
                role.setRoleId(Long.valueOf(request.getParameter("id")));

                // Validate the book data
                Map<String, String> errors = validateRole(role);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("role", role);
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/WEB-INF/views/roles/form-role.jsp")
                            .forward(request, response);
                    return;
                }
                roleService.update(role);
                response.sendRedirect(request.getContextPath() + "/roles/all");
            } else if (path.equals("/delete")) {
                String idParam = request.getParameter("id");
                if (idParam == null || idParam.isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                roleService.delete(Long.valueOf(idParam));
                response.sendRedirect(request.getContextPath() + "/roles/all");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Map<String, String> validateRole(Role role) {
        Map<String, String> errors = new HashMap<>();

        if (role.getName() == null || role.getName().isBlank()) {
            errors.put("name", "El nombre no puede ser nulo o vacío.");
        }

        if (role.getDescription() == null || role.getDescription().isBlank()) {
            errors.put("description", "La descripción no puede ser nula o vacía.");
        }

        return errors;
    }

    private Role buildRole(HttpServletRequest request, HttpServletResponse response) {
        Role role = new Role();
        role.setName(request.getParameter("name"));
        role.setDescription(request.getParameter("description"));

        return role;
    }
}
