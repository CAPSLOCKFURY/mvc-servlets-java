package commands.impl;

import commands.Command;
import commands.CommandResult;
import commands.RequestDirection;
import forms.LoginForm;
import forms.base.prg.CookieFormErrorUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginPostCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LoginForm form = new LoginForm();
        form.mapRequestToForm(request);
        boolean isValid = form.validate();
        //TODO do this only if form has errors
        response.addCookie(CookieFormErrorUtils.setErrorCookie(form.getErrors()));
        if(isValid){
            return new CommandResult("/MVCProject_war_exploded/project", RequestDirection.REDIRECT);
        } else {
            return new CommandResult("/MVCProject_war_exploded/project/login", RequestDirection.REDIRECT);
        }
    }
}
