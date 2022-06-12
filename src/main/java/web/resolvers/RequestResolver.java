package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.WebMethodArgumentResolver;

public class RequestResolver implements WebMethodArgumentResolver<HttpServletRequest> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved) {
        return request;
    }
}
