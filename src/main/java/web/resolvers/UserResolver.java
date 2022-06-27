package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import web.base.argument.resolvers.WebMethodArgumentResolver;

import java.lang.reflect.Parameter;

public class UserResolver implements WebMethodArgumentResolver<User> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter parameter) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }
}
