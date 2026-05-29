package dev.gustavorh.lms_dev_10.presentation.controllers;

import dev.gustavorh.lms_dev_10.infrastructure.config.DbContext;
import dev.gustavorh.lms_dev_10.domain.entities.Permission;
import dev.gustavorh.lms_dev_10.domain.entities.Role;
import dev.gustavorh.lms_dev_10.domain.entities.RolePermissions;
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

@WebServlet({"/role_permissions","/role_permissions/*"})
public class RolePermissionsServlet extends HttpServlet {
    private IService<RolePermissions> rolePermissionsService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbContext.getConnection();
            IRepositoryFactory repositoryFactory = new JdbcRepositoryFactory(connection);
            IServiceFactory serviceFactory = new DefaultServiceFactory(repositoryFactory);

            rolePermissionsService = serviceFactory.createRolePermissionsService();
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
                request.setAttribute("rolePermissions", rolePermissionsService.findAll());
                request.getRequestDispatcher("/WEB-INF/views/role_permissions/role_permissions.jsp").forward(request, response);
                break;
            case "/create":
                request.setAttribute("rolePermission", new RolePermissions()); // Empty book for the form
                request.setAttribute("action", "create");
                request.getRequestDispatcher("/WEB-INF/views/role_permissions/form-role_permission.jsp").forward(request, response);
                break;
            case "/edit":
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                request.setAttribute("rolePermission", rolePermissionsService.findById(Long.valueOf(request.getParameter("id"))).get());
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("/WEB-INF/views/role_permissions/form-role_permission.jsp").forward(request, response);
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
                RolePermissions rolePermission = buildRolePermissions(request, response);

                // Validate the book data
                Map<String, String> errors = validateRolePermissions(rolePermission);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("rolePermission", rolePermission);
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("/WEB-INF/views/role_permissions/form-role_permission.jsp")
                            .forward(request, response);
                    return;
                }
                rolePermissionsService.save(rolePermission);
                // Redirect to book list
                response.sendRedirect(request.getContextPath() + "/role_permissions/all");
            } else if (path.equals("/edit")) {
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                RolePermissions rolePermission = buildRolePermissions(request, response);

                // Validate the book data
                Map<String, String> errors = validateRolePermissions(rolePermission);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("rolePermission", rolePermission);
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/WEB-INF/views/role_permissions/form-role_permission.jsp")
                            .forward(request, response);
                    return;
                }
                rolePermissionsService.update(rolePermission);
                response.sendRedirect(request.getContextPath() + "/role_permissions/all");
            } else if (path.equals("/delete")) {
                String idParam = request.getParameter("id");
                if (idParam == null || idParam.isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                rolePermissionsService.delete(Long.valueOf(idParam));
                response.sendRedirect(request.getContextPath() + "/role_permissions/all");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Map<String, String> validateRolePermissions(RolePermissions rolePermissions) {
        Map<String, String> errors = new HashMap<>();


        return errors;
    }

    private RolePermissions buildRolePermissions(HttpServletRequest request, HttpServletResponse response) {
        RolePermissions rolePermissions = new RolePermissions();
        Role role = new Role();
        Permission permission = new Permission();

        role.setRoleId(Long.valueOf(request.getParameter("id_rol")));
        role.setName(request.getParameter("nombre_rol"));
        rolePermissions.setRole(role);
        permission.setPermissionId(Long.valueOf(request.getParameter("id_permiso")));
        permission.setName(request.getParameter("nombre_permiso"));
        rolePermissions.setPermission(permission);

        return rolePermissions;
    }
}
