package dev.gustavorh.lms_dev_10.presentation.controllers;

import dev.gustavorh.lms_dev_10.infrastructure.config.DbContext;
import dev.gustavorh.lms_dev_10.domain.entities.Category;
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

@WebServlet({"/categories","/categories/*"})
public class CategoryServlet extends HttpServlet {
    private IService<Category> categoryService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbContext.getConnection();
            IRepositoryFactory repositoryFactory = new JdbcRepositoryFactory(connection);
            IServiceFactory serviceFactory = new DefaultServiceFactory(repositoryFactory);

            categoryService = serviceFactory.createCategoryService();
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
                request.setAttribute("categories", categoryService.findAll());
                request.getRequestDispatcher("/WEB-INF/views/categories/categories.jsp").forward(request, response);
                break;
            case "/create":
                request.setAttribute("category", new Category());
                request.setAttribute("action", "create");
                request.getRequestDispatcher("/WEB-INF/views/categories/form-category.jsp").forward(request, response);
                break;
            case "/edit":
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                request.setAttribute("category", categoryService.findById(Long.valueOf(request.getParameter("id"))).get());
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("/WEB-INF/views/categories/form-category.jsp").forward(request, response);
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
                Category category = buildCategory(request);

                Map<String, String> errors = validateCategory(category);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("category", category);
                    request.setAttribute("action", "create");
                    request.getRequestDispatcher("/WEB-INF/views/categories/form-category.jsp")
                            .forward(request, response);
                    return;
                }
                categoryService.save(category);
                response.sendRedirect(request.getContextPath() + "/categories/all");
            } else if (path.equals("/edit")) {
                if (request.getParameter("id") == null || request.getParameter("id").isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Category category = buildCategory(request);
                category.setCategoryId(Long.valueOf(request.getParameter("id")));

                Map<String, String> errors = validateCategory(category);

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("category", category);
                    request.setAttribute("action", "edit");
                    request.getRequestDispatcher("/WEB-INF/views/categories/form-category.jsp")
                            .forward(request, response);
                    return;
                }
                categoryService.update(category);
                response.sendRedirect(request.getContextPath() + "/categories/all");
            } else if (path.equals("/delete")) {
                String idParam = request.getParameter("id");
                if (idParam == null || idParam.isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                categoryService.delete(Long.valueOf(idParam));
                response.sendRedirect(request.getContextPath() + "/categories/all");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Map<String, String> validateCategory(Category category) {
        Map<String, String> errors = new HashMap<>();

        if (category.getName() == null || category.getName().isBlank()) {
            errors.put("name", "El nombre no puede estar vacío.");
        }

        return errors;
    }

    private Category buildCategory(HttpServletRequest request) {
        Category c = new Category();
        c.setName(request.getParameter("name"));
        return c;
    }
}
