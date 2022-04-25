package web.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Base interface for all web commands
 */
@FunctionalInterface
public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
