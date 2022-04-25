package web.base.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Base interface for implementing security
 */
public interface Security {
    /**
     * @return True if security passes, and false if not
     */
    boolean doSecurity(HttpServletRequest request, HttpServletResponse response);
}
