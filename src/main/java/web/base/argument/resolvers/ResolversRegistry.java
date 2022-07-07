package web.base.argument.resolvers;

import context.ApplicationContext;
import context.ContextHolder;
import scanner.ClassPathScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

public class ResolversRegistry {

    private static final ApplicationContext context = ContextHolder.getInstance().getApplicationContext();

    private Map<Class<?>, WebMethodArgumentResolver<?>> resolverMap;

    private final static ResolversRegistry instance = new ResolversRegistry();

    public static ResolversRegistry getInstance() {
        return instance;
    }

    private ResolversRegistry(){
        resolverMap = getArgumentResolversMap(getResolverClasses());
    }

    public WebMethodArgumentResolver<?> getResolver(Class<?> type){
        return resolverMap.get(type);
    }

    private Set<Class<?>> getResolverClasses(){
        ClassPathScanner classPathScanner = new ClassPathScanner();
        return classPathScanner.scan(context.argumentResolversPackage(), WebMethodArgumentResolver.class::isAssignableFrom);
    }

    private Map<Class<?>, WebMethodArgumentResolver<?>> getArgumentResolversMap(Set<Class<?>> resolvers){
        Map<Class<?>, WebMethodArgumentResolver<?>> methodArgumentResolverMap = new HashMap<>();
        resolvers.forEach(r -> {
                    try {
                        methodArgumentResolverMap.put(getResolverArgumentType(r), (WebMethodArgumentResolver<?>) r.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
        return methodArgumentResolverMap;
    }

    private Class<?> getResolverArgumentType(Class<?> resolver){
        List<ParameterizedType> parameterizedTypes = Arrays.stream(resolver.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .filter(t -> ((ParameterizedType) t).getRawType().equals(WebMethodArgumentResolver.class))
                .map(t -> (ParameterizedType) t)
                .collect(Collectors.toList());
        if(parameterizedTypes.size() != 1){
            throw new IllegalStateException("Parametrized type is not 1 on " + resolver.getSimpleName());
        }
        return (Class<?>)parameterizedTypes.get(0).getActualTypeArguments()[0];
    }

}
