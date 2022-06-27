package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.WebMethodArgumentResolver;

import java.lang.reflect.Parameter;

public class ResponseResolver implements WebMethodArgumentResolver<HttpServletResponse> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter parameter) {
        return response;
    }
}
