package web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.ResolversRegistry;
import web.base.argument.resolvers.WebMethodArgumentResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class ArgumentResolver {

    private static final ResolversRegistry resolversRegistry = ResolversRegistry.getInstance();

    private final Method method;

    public ArgumentResolver(Method method){
        this.method = method;
    }

    public Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response){
        Parameter[] parameters = method.getParameters();
        List<Object> resolvedArguments = new LinkedList<>();
        for (Parameter p : parameters){
            Object previousResolved = null;
            boolean annotationResolved = false;
            Annotation[] annotations = p.getAnnotations();
            for (Annotation a : annotations){
                previousResolved = resolveAnnotation(request, response, previousResolved, a, p);
                annotationResolved = true;
            }
            if(annotationResolved){
                if(previousResolved == null || (previousResolved.getClass().equals(p.getType()) || p.getType().isAssignableFrom(previousResolved.getClass()))) {
                    resolvedArguments.add(previousResolved);
                } else {
                    resolvedArguments.add(resolveParameter(request, response, previousResolved, p));
                }
            } else {
                resolvedArguments.add(resolveParameter(request, response, null, p));
            }
        }
        return resolvedArguments.toArray();
    }

    private Object resolveParameter(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter parameter){
        WebMethodArgumentResolver<?> parameterResolver = resolversRegistry.getResolver(parameter.getType());
        return parameterResolver.resolve(request, response, previousResolved, parameter);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object resolveAnnotation(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Annotation annotation, Parameter parameter){
        WebMethodArgumentResolver resolver = resolversRegistry.getResolver(annotation.annotationType());
        return resolver.resolve(request, response, previousResolved, parameter, annotation);
    }

}
