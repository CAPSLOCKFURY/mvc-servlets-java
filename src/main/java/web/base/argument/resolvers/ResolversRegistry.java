package web.base.argument.resolvers;

import utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResolversRegistry {

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

    private List<Class<?>> getResolverClasses(){
        return ClassUtils.getClassesInPackage("web.resolvers", WebMethodArgumentResolver.class::isAssignableFrom);
    }

    private Map<Class<?>, WebMethodArgumentResolver<?>> getArgumentResolversMap(List<Class<?>> resolvers){
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
