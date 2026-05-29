package dev.gustavorh.lms_dev_10.presentation.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates a CSRF synchronizer token and validates it on all state-changing requests (POST/PUT/DELETE).
 * The token is stored in the HTTP session and must be submitted as the "_csrf" request parameter.
 */
@WebFilter("/*")
public class CsrfFilter implements Filter {
    public static final String CSRF_TOKEN_ATTR = "csrfToken";
    public static final String CSRF_PARAM = "_csrf";

    private static final Logger log = Logger.getLogger(CsrfFilter.class.getName());

    private static final List<String> SAFE_METHODS = Arrays.asList("GET", "HEAD", "OPTIONS");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession();

        // Ensure a CSRF token exists in the session
        if (session.getAttribute(CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(CSRF_TOKEN_ATTR, UUID.randomUUID().toString());
        }

        // Expose token as request attribute so JSPs can read it via ${csrfToken}
        httpReq.setAttribute(CSRF_TOKEN_ATTR, session.getAttribute(CSRF_TOKEN_ATTR));

        if (!SAFE_METHODS.contains(httpReq.getMethod())) {
            String sessionToken = (String) session.getAttribute(CSRF_TOKEN_ATTR);
            String requestToken = httpReq.getParameter(CSRF_PARAM);

            if (sessionToken == null || !sessionToken.equals(requestToken)) {
                log.log(Level.WARNING, "CSRF token mismatch for {0} {1}",
                        new Object[]{httpReq.getMethod(), httpReq.getRequestURI()});
                httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
