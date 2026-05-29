package dev.gustavorh.lms_dev_10.presentation.filters;

import dev.gustavorh.lms_dev_10.infrastructure.config.DbContext;
import dev.gustavorh.lms_dev_10.domain.exceptions.ServiceException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter("/*")
public class QueryFilter implements Filter {
    private static final Logger log = Logger.getLogger(QueryFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try (Connection conn = DbContext.getConnection()) {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                chain.doFilter(request, response);
                conn.commit();
            } catch (SQLException | ServiceException e) {
                safeRollback(conn, e);
                log.log(Level.SEVERE, "Transaction error — rolling back", e);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (ServletException | IOException e) {
                // Checked exceptions from the servlet chain — rollback and re-throw so Tomcat handles them
                safeRollback(conn, e);
                throw e;
            } catch (RuntimeException e) {
                safeRollback(conn, e);
                log.log(Level.SEVERE, "Unexpected runtime error — rolling back", e);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Failed to obtain database connection", e);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }

    private void safeRollback(Connection conn, Throwable cause) {
        try {
            conn.rollback();
        } catch (SQLException rollbackEx) {
            log.log(Level.SEVERE, "Rollback failed after error: " + cause.getMessage(), rollbackEx);
        }
    }
}
