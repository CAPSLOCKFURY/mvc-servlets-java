package web;

import exceptions.WebMethodNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import scanner.ClassPathScanner;
import web.base.RequestMethod;
import web.base.UrlBind;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ControllerRegistry {

    private final Map<UrlBind, Method> methodMap = new HashMap<>();

    private static final Map<Class<?>, Object> controllers = new HashMap<>();

    private static final ControllerRegistry instance = new ControllerRegistry();

    private ControllerRegistry(){
        registerControllerInstances();
        registerControllerWebMethods(new ArrayList<>(controllers.keySet()));
    }

    public static ControllerRegistry getInstance(){
        return instance;
    }

    public Method resolveMethod(HttpServletRequest request) throws WebMethodNotFoundException {
        String url = getRequestUrl(request);
        UrlBind urlBind = new UrlBind(url, RequestMethod.valueOf(request.getMethod()));
        return Optional.ofNullable(methodMap.get(urlBind))
                .orElseThrow(WebMethodNotFoundException::new);
    }

    public Object getControllerObject(Class<?> controllerClass){
        return controllers.get(controllerClass);
    }

    private static String getRequestUrl(HttpServletRequest request){
        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            return "";
        }
        return pathInfo.replaceAll(request.getServerName(), "");
    }

    private void registerControllerInstances(){
        ClassPathScanner classPathScanner = new ClassPathScanner();
        Set<Class<?>> controllers = classPathScanner.scan("controllers", c -> c.isAnnotationPresent(WebController.class));
        controllers.forEach(c -> {
            try {
                Object controller = c.getConstructor().newInstance();
                ControllerRegistry.controllers.put(controller.getClass(), controller);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerControllerWebMethods(List<Class<?>> controllers){
        controllers.stream().flatMap(c -> Arrays.stream(c.getMethods()))
                .filter(m -> m.isAnnotationPresent(WebMapping.class))
                .forEach(m -> {
                    WebMapping webMapping = m.getAnnotation(WebMapping.class);
                    methodMap.put(new UrlBind(webMapping.url(), webMapping.method()), m);
                });
    }
}
