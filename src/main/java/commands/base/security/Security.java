package commands.base.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Security {
    boolean doSecurity(HttpServletRequest request, HttpServletResponse response);
}
