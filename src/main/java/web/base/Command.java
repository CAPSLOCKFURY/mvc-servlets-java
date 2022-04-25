package web.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Base interface for all web commands
 */
@FunctionalInterface
@Deprecated
public interface Command {
    WebResult execute(HttpServletRequest request, HttpServletResponse response);
}
