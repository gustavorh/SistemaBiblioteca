package dev.gustavorh.lms_dev_10.controllers.books;

import dev.gustavorh.lms_dev_10.config.DbContext;
import dev.gustavorh.lms_dev_10.entities.Author;
import dev.gustavorh.lms_dev_10.entities.Book;
import dev.gustavorh.lms_dev_10.entities.Category;
import dev.gustavorh.lms_dev_10.factories.implementations.DefaultServiceFactory;
import dev.gustavorh.lms_dev_10.factories.implementations.JdbcRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IRepositoryFactory;
import dev.gustavorh.lms_dev_10.factories.interfaces.IServiceFactory;
import dev.gustavorh.lms_dev_10.services.interfaces.IService;
import dev.gustavorh.lms_dev_10.utils.ServiceException;
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

@WebServlet(urlPatterns = {"/books/add", "/books/edit"})
public class BookFormServlet extends HttpServlet {
    private IService<Book> bookService;
    private IService<Author> authorService;
    private IService<Category> categoryService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbContext.getConnection();
            IRepositoryFactory repositoryFactory = new JdbcRepositoryFactory(connection);
            IServiceFactory serviceFactory = new DefaultServiceFactory(repositoryFactory);

            bookService = serviceFactory.createBookService();
            authorService = serviceFactory.createAuthorService();
            categoryService = serviceFactory.createCategoryService();
        } catch (SQLException e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            // Load authors and categories for the form
            request.setAttribute("authors", authorService.findAll());
            request.setAttribute("categories", categoryService.findAll());

            if ("/books/edit".equals(path)) {
                Long bookId = Long.valueOf(request.getParameter("id"));
                Book book = bookService.findById(bookId);
                if (book == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                request.setAttribute("book", book);
                request.setAttribute("action", "edit");
            } else {
                request.setAttribute("book", new Book()); // Empty book for the form
                request.setAttribute("action", "add");
            }

            request.getRequestDispatcher("/WEB-INF/views/books/form-book.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ServletException("Error processing book form", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Book book = new Book();

            // If editing, get the existing book id
            String bookId = request.getParameter("id");
            if (bookId != null && !bookId.isEmpty()) {
                book.setBookId(Long.valueOf(bookId));
            }

            // Populate book object from form parameters
            book.setTitle(request.getParameter("title"));
            book.setIsbn(request.getParameter("isbn"));
            book.setPublicationYear(Integer.parseInt(request.getParameter("publicationYear")));

            // Set author
            Author author = new Author();
            author.setAuthorId(Long.valueOf(request.getParameter("authorId")));
            book.setAuthor(author);

            // Set category
            Category category = new Category();
            category.setCategoryId(Long.valueOf(request.getParameter("categoryId")));
            book.setCategory(category);

            // Validate the book data
            Map<String, String> errors = validateBook(book);

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("book", book);
                request.setAttribute("authors", authorService.findAll());
                request.setAttribute("categories", categoryService.findAll());
                request.setAttribute("action", book.getBookId() == 0 ? "add" : "edit");
                request.getRequestDispatcher("/WEB-INF/views/book-form.jsp")
                        .forward(request, response);
                return;
            }

            // Save or update the book
            if (book.getBookId() == 0) {
                bookService.save(book);
            } else {
                bookService.update(book);
            }

            // Redirect to book list
            response.sendRedirect(request.getContextPath() + "/books");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ServletException("Error saving book", e);
        }
    }

    private Map<String, String> validateBook(Book book) {
        Map<String, String> errors = new HashMap<>();

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            errors.put("title", "Title is required");
        }

        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            errors.put("isbn", "ISBN is required");
        }

        if (book.getPublicationYear() <= 0) {
            errors.put("publicationYear", "Invalid publication year");
        }

        if (book.getAuthor() == null || book.getAuthor().getAuthorId() <= 0) {
            errors.put("authorId", "Author is required");
        }

        if (book.getCategory() == null || book.getCategory().getCategoryId() <= 0) {
            errors.put("categoryId", "Category is required");
        }

        return errors;
    }
}
