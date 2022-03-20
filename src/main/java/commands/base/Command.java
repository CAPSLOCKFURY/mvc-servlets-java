package commands.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Base interface for all web commands, commands can also be written as lammbdas in {@link commands.CommandRegistry}
 */
@FunctionalInterface
public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
