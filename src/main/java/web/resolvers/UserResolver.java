package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import web.base.argument.resolvers.WebMethodArgumentResolver;

public class UserResolver implements WebMethodArgumentResolver<User> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }
}
