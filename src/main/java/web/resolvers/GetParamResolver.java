package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.WebMethodArgumentResolver;
import web.resolvers.annotations.GetParameter;

import java.lang.reflect.Parameter;

public class GetParamResolver implements WebMethodArgumentResolver<GetParameter> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter parameter, GetParameter param) {
        String requestParameter = request.getParameter(param.value());
        if(requestParameter == null && param.required()){
            throw new IllegalArgumentException("Get parameter " + param.value() + " is required");
        }
        return requestParameter;
    }
}
