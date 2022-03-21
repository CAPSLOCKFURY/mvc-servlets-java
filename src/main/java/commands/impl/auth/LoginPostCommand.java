package commands.impl.auth;

import commands.base.*;
import commands.base.security.NonAuthenticatedOnly;
import commands.base.security.Security;
import forms.LoginForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.UserService;
import utils.LocaleUtils;

import java.util.Locale;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/login", method = RequestMethod.POST)
public class LoginPostCommand implements Command {

    private final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new NonAuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        LoginForm form = new LoginForm();
        form.mapRequestToForm(request);
        form.setLocale(new Locale(LocaleUtils.getLocaleFromCookies(request.getCookies())));
        boolean isValid = form.validate();
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/login", request), RequestDirection.REDIRECT);
        }
        User user = userService.loginUser(form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/login", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return new CommandResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
    }
}
