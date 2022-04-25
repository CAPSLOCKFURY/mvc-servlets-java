package web.base.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

public class ManagerOnly implements Security{
    @Override
    public boolean doSecurity(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return false;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user.getRole() == 2;
    }
}
