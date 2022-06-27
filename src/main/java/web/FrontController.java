package web;

import exceptions.WebMethodNotFoundException;
import exceptions.security.SecurityNoRedirectUrl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import web.base.RequestDirection;
import web.base.WebResult;
import web.base.security.Security;
import web.base.security.SecurityStrategyFactory;
import web.base.security.annotations.AuthenticatedOnly;
import web.base.security.annotations.ManagerOnly;
import web.base.security.annotations.NonAuthenticatedOnly;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static utils.UrlUtils.getAbsoluteUrl;

/**
 * Front-controller servlet which handles all requests
 */
public class FrontController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final ControllerRegistry controllerRegistry = ControllerRegistry.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            logger.debug("Started handling request");
            Method method = controllerRegistry.resolveMethod(request);
            logger.debug("Resolved web method");
            Annotation securityAnnotation = getSecurityAnnotation(method);
            if(securityAnnotation != null){
                Security security = SecurityStrategyFactory.getStrategy(securityAnnotation);
                boolean securityResult = security.doSecurity(request, response);
                if(!securityResult){
                    WebResult securityWebResult = new WebResult(getSecurityAnnotationRedirectUrl(securityAnnotation), RequestDirection.REDIRECT);
                    handleWebResult(securityWebResult, request, response);
                    logger.debug("Method did {} not passed web security", method.getName());
                    return;
                }
            }
            Object controllerInstance = controllerRegistry.getControllerObject(method.getDeclaringClass());
            ArgumentResolver argumentResolver = new ArgumentResolver(method);
            Object[] arguments = argumentResolver.resolveArguments(request, response);
            WebResult result = (WebResult) method.invoke(controllerInstance, arguments);
            logger.debug("Executed web method");
            handleWebResult(result, request, response);
        } catch (WebMethodNotFoundException e){
            logger.error(e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }

    private void handleWebResult(WebResult webResult, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(webResult.getDirection() == RequestDirection.FORWARD){
            request.getRequestDispatcher("/pages/" + webResult.getUrl()).forward(request, response);
        } else if(webResult.getDirection() == RequestDirection.REDIRECT){
            if(webResult.isAbsolute()){
                response.sendRedirect(webResult.getUrl());
            } else {
                response.sendRedirect(getAbsoluteUrl(webResult.getUrl(), request));
            }
        } else if(webResult.getDirection() == RequestDirection.VOID){
            //this intentionally left blank
        }
    }

    private Annotation getSecurityAnnotation(Method method){
        if(method.isAnnotationPresent(AuthenticatedOnly.class)){
            return method.getAnnotation(AuthenticatedOnly.class);
        } else if (method.isAnnotationPresent(ManagerOnly.class)){
            return method.getAnnotation(ManagerOnly.class);
        } else if (method.isAnnotationPresent(NonAuthenticatedOnly.class)){
            return method.getAnnotation(NonAuthenticatedOnly.class);
        }
        return null;
    }

    private String getSecurityAnnotationRedirectUrl(Annotation securityAnnotation){
        try {
            return (String) securityAnnotation.getClass().getMethod("value").invoke(securityAnnotation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new SecurityNoRedirectUrl("No redirect url attribute on annotation: " + securityAnnotation.annotationType().getSimpleName());
        }
    }
}