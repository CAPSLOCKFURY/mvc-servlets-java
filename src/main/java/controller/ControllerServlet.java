package controller;

import java.io.*;

import commands.Command;
import commands.CommandRegistry;
import commands.CommandResult;
import commands.utils.RequestDirection;
import exceptions.CommandNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class ControllerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            System.out.println("Started handling request");
            CommandRegistry commandRegistry = CommandRegistry.getInstance();
            System.out.println("Got command registry");
            Command command = commandRegistry.resolveCommand(request);
            System.out.println("Resolved command");
            CommandResult result = command.execute(request, response);
            System.out.println("Executed command");
            if(result.getDirection() == RequestDirection.FORWARD){
                request.getRequestDispatcher("/pages/" + result.getJspPage()).forward(request, response);
            } else if(result.getDirection() == RequestDirection.REDIRECT){
                response.sendRedirect(result.getJspPage());
            }
        } catch (CommandNotFoundException e){
            System.err.println(e.getMessage());
        }
    }
}