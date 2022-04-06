package controller;

import commands.CommandRegistry;
import commands.base.Command;
import commands.base.CommandResult;
import commands.base.RequestDirection;
import exceptions.CommandNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Front-controller servlet which handles all requests
 */
public class ControllerServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final CommandRegistry commandRegistry = CommandRegistry.getInstance();

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
            Command command = commandRegistry.resolveCommand(request);
            logger.debug("Resolved command");
            CommandResult result = command.execute(request, response);
            logger.debug("Executed command");
            if(result.getDirection() == RequestDirection.FORWARD){
                request.getRequestDispatcher("/pages/" + result.getUrl()).forward(request, response);
            } else if(result.getDirection() == RequestDirection.REDIRECT){
                response.sendRedirect(result.getUrl());
            } else if(result.getDirection() == RequestDirection.VOID){
                //this intentionally left blank
            }
        } catch (CommandNotFoundException e){
            logger.error(e.getMessage());
        }
    }
}