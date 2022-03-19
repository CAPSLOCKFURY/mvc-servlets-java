package commands.impl.profile;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import forms.ChangePasswordForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.UserService;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/change-password", method = RequestMethod.POST)
public class ChangePasswordPost implements Command {

    private final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        ChangePasswordForm form = new ChangePasswordForm();
        form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        form.mapRequestToForm(request);
        if(!form.validate()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        HttpSession session = request.getSession();
        Long userId = ((User) session.getAttribute("user")).getId();
        boolean isChanged = userService.changeUserPassword(form, userId);
        if(!isChanged){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/profile/change-password", request), RequestDirection.REDIRECT);
        }
        session.invalidate();
        return new CommandResult(getAbsoluteUrl("/login", request), RequestDirection.REDIRECT);
    }
}
