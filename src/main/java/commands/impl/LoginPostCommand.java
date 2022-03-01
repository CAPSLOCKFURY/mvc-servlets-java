package commands.impl;

import commands.Command;
import commands.CommandResult;
import commands.utils.RequestDirection;
import forms.LoginForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utils.UrlUtils.getAbsoluteUrl;

public class LoginPostCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LoginForm form = new LoginForm();
        form.mapRequestToForm(request);
        boolean isValid = form.validate();
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        if(isValid){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(getAbsoluteUrl("/login", request), RequestDirection.REDIRECT);
        }
    }
}
