package commands.impl;

import commands.Command;
import commands.CommandResult;
import commands.RequestDirection;
import forms.RegisterForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterPostCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RegisterForm form = new RegisterForm();
        form.mapRequestToForm(form, request);
        boolean isValid = form.validate();
        request.setAttribute("errors", form.getErrors());
        if(isValid){
            //TODO Fix urls
            return new CommandResult("/MVCProject_war_exploded/project", RequestDirection.REDIRECT);
        } else {
            //TODO Add prg
            return new CommandResult("register.jsp", RequestDirection.FORWARD);
        }
    }
}
