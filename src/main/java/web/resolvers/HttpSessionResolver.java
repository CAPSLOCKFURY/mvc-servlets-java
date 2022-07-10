package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.base.argument.resolvers.WebMethodArgumentResolver;

import java.lang.reflect.Parameter;

public class HttpSessionResolver implements WebMethodArgumentResolver<HttpSession> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter methodParam) {
        return request.getSession();
    }
}
