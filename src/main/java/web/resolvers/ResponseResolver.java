package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.WebMethodArgumentResolver;

public class ResponseResolver implements WebMethodArgumentResolver<HttpServletResponse> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved) {
        return response;
    }
}
