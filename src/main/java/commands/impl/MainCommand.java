package commands.impl;

import commands.Command;
import commands.CommandResult;
import commands.utils.RequestDirection;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response){
        return new CommandResult("/index.jsp", RequestDirection.FORWARD);
    }

}
