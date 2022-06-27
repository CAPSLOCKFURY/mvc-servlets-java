package web.base.argument.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;

public interface WebMethodArgumentResolver<A> {

    default Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter methodParam, A annotation){
        return null;
    }

    default Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter methodParam){
        return null;
    }

}
