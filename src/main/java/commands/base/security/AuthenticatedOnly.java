package commands.base.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticatedOnly implements Security{
    @Override
    public boolean doSecurity(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
    }
}
