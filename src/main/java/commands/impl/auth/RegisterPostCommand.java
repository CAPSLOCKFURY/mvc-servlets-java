package commands.impl.auth;

import commands.base.*;
import forms.RegisterForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.UserService;
import utils.LocaleUtils;

import java.util.Locale;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/register", method = RequestMethod.POST)
public class RegisterPostCommand implements Command {

    private final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RegisterForm form = new RegisterForm();
        form.mapRequestToForm(request);
        form.setLocale(new Locale(LocaleUtils.getLocaleFromCookies(request.getCookies())));
        boolean isValid = form.validate();
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/register", request), RequestDirection.REDIRECT);
        }
        long id = userService.createUser(form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/register", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = new User(form);
        user.setId(id);
        session.setAttribute("user", user);
        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);

    }
}
