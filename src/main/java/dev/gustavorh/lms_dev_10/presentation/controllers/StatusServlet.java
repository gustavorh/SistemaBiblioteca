package dev.gustavorh.lms_dev_10.presentation.controllers;

import dev.gustavorh.lms_dev_10.infrastructure.config.DbContext;
import dev.gustavorh.lms_dev_10.domain.entities.Status;
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

@WebServlet({"/statuses","/statuses/*"})
public class StatusServlet extends HttpServlet {
    private IService<Status> statusService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbContext.getConnection();
            IRepositoryFactory repositoryFactory = new JdbcRepositoryFactory(connection);
            IServiceFactory serviceFactory = new DefaultServiceFactory(repositoryFactory);

            statusService = serviceFactory.createStatusService();
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
                request.setAttribute("statuses", statusService.findAll());
                request.getRequestDispatcher("/WEB-INF/views/statuses/statuses.jsp").forward(request, response);
                break;
            case "/create":
                request.setAttribute("status", new Status()); // Empty book for the form
                request.setAttribute("action", "create");
                request.getRequestDispatcher("/WEB-INF/views/statuses/form-status.jsp").forward(request, response);
                break;
            case "/edit":
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                request.setAttribute("status", statusService.findById(Long.valueOf(request.getParameter("id"))).get());
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("/WEB-INF/views/statuses/form-status.jsp").forward(request, response);
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
                Status status = buildStatus(request, response);

                // Validate the book data
                Map<String, String> errors = validateStatus(status);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("status", status);
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("/WEB-INF/views/statuses/form-status.jsp")
                            .forward(request, response);
                    return;
                }
                statusService.save(status);
                // Redirect to book list
                response.sendRedirect(request.getContextPath() + "/statuses/all");
            } else if (path.equals("/edit")) {
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Status status = buildStatus(request, response);
                status.setStatusId(Long.valueOf(request.getParameter("id")));

                // Validate the book data
                Map<String, String> errors = validateStatus(status);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("status", status);
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/WEB-INF/views/statuses/form-status.jsp")
                            .forward(request, response);
                    return;
                }
                statusService.update(status);
                response.sendRedirect(request.getContextPath() + "/statuses/all");
            } else if (path.equals("/delete")) {
                String idParam = request.getParameter("id");
                if (idParam == null || idParam.isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                statusService.delete(Long.valueOf(idParam));
                response.sendRedirect(request.getContextPath() + "/statuses/all");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Map<String, String> validateStatus(Status status) {
        Map<String, String> errors = new HashMap<>();

        if (status.getName() == null || status.getName().isBlank()) {
            errors.put("name", "El nombre no puede estar vacío.");
        }

        return errors;
    }

    private Status buildStatus(HttpServletRequest request, HttpServletResponse response) {
        Status status = new Status();
        status.setName(request.getParameter("name"));
        status.setDescription(request.getParameter("description"));
        return status;
    }
}
