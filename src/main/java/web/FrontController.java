package web;

import exceptions.WebMethodNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import web.base.RequestDirection;
import web.base.WebResult;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            logger.debug("Resolved command");
            Object controllerInstance = controllerRegistry.getControllerObject(method.getDeclaringClass());
            WebResult result = (WebResult) method.invoke(controllerInstance, request, response);
            logger.debug("Executed web method");
            if(result.getDirection() == RequestDirection.FORWARD){
                request.getRequestDispatcher("/pages/" + result.getUrl()).forward(request, response);
            } else if(result.getDirection() == RequestDirection.REDIRECT){
                response.sendRedirect(result.getUrl());
            } else if(result.getDirection() == RequestDirection.VOID){
                //this intentionally left blank
            }
        } catch (WebMethodNotFoundException e){
            logger.error(e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }
}