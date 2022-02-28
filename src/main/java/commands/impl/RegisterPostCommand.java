package commands.impl;

import commands.Command;
import commands.CommandResult;
import commands.utils.RequestDirection;
import forms.RegisterForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterPostCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RegisterForm form = new RegisterForm();
        form.mapRequestToForm(request);
        boolean isValid = form.validate();
        response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        if(isValid){
            //TODO Fix urls
            return new CommandResult("/MVCProject_war_exploded/project", RequestDirection.REDIRECT);
        } else {
            return new CommandResult("/MVCProject_war_exploded/project/register", RequestDirection.REDIRECT);
        }
    }
}
