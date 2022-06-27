package web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.ResolversRegistry;
import web.base.argument.resolvers.WebMethodArgumentResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ArgumentResolver {

    private static final ResolversRegistry resolversRegistry = ResolversRegistry.getInstance();

    private final Method method;

    public ArgumentResolver(Method method){
        this.method = method;
    }

    public Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response){
        AtomicReference<Object> previousResolved = new AtomicReference<>();
        AtomicBoolean annotationResolved = new AtomicBoolean();
        Parameter[] parameters = method.getParameters();
        List<Object> resolvedArguments = new LinkedList<>();
        Arrays.stream(parameters).forEach(p -> {
            previousResolved.set(null);
            annotationResolved.set(false);
            Annotation[] annotations = p.getAnnotations();
            Arrays.stream(annotations).forEach(a -> {
                WebMethodArgumentResolver resolver = resolversRegistry.getResolver(a.annotationType());
                if(resolver != null) {
                    Object prevResolved = resolveAnnotation(request, response, previousResolved, a);
                    previousResolved.set(prevResolved);
                    annotationResolved.set(true);
                }
            });
            if(annotationResolved.get()){
                if(previousResolved.get() == null || (previousResolved.get().getClass().equals(p.getType()) || p.getType().isAssignableFrom(previousResolved.get().getClass()))) {
                    resolvedArguments.add(previousResolved.get());
                } else {
                    resolvedArguments.add(resolveParameter(request, response, previousResolved, p));
                }
            } else {
                resolvedArguments.add(resolveParameter(request, response, previousResolved, p));
            }
        });
        return resolvedArguments.toArray();
    }

    private Object resolveParameter(HttpServletRequest request, HttpServletResponse response, AtomicReference<Object> previousResolved, Parameter parameter){
        WebMethodArgumentResolver<?> parameterResolver = resolversRegistry.getResolver(parameter.getType());
        return parameterResolver.resolve(request, response, previousResolved.get());
    }

    private Object resolveAnnotation(HttpServletRequest request, HttpServletResponse response, AtomicReference<Object> previousResolved, Annotation annotation){
        WebMethodArgumentResolver resolver = resolversRegistry.getResolver(annotation.annotationType());
        Object resolvedValue = resolver.resolve(request, response, previousResolved.get(), annotation);
        previousResolved.set(resolvedValue);
        return resolvedValue;
    }

}
