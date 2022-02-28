package commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
