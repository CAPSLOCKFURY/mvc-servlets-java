package web.base.argument.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface WebMethodArgumentResolver<A> {

    default Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, A param){
        return null;
    }

    default Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved){
        return resolve(request, response, previousResolved, null);
    }

}
