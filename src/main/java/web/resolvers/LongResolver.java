package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.WebMethodArgumentResolver;

public class LongResolver implements WebMethodArgumentResolver<Long> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved) {
        if(previousResolved instanceof String){
            return Long.parseLong((String) previousResolved);
        } else {
            throw new IllegalArgumentException("Illegal type for long parameter " + previousResolved.getClass());
        }
    }
}
