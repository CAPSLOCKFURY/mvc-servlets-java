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

public class ControllerServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            logger.debug("Started handling request");
            CommandRegistry commandRegistry = CommandRegistry.getInstance();
            Command command = commandRegistry.resolveCommand(request);
            logger.debug("Resolved command");
            CommandResult result = command.execute(request, response);
            logger.debug("Executed command");
            if(result.getDirection() == RequestDirection.FORWARD){
                request.getRequestDispatcher("/pages/" + result.getJspPage()).forward(request, response);
            } else if(result.getDirection() == RequestDirection.REDIRECT){
                response.sendRedirect(result.getJspPage());
            }
        } catch (CommandNotFoundException e){
            logger.error(e.getMessage());
        }
    }
}