package commands.impl.profile;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import forms.AddBalanceForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.UserService;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/balance", method = RequestMethod.POST)
public class BalancePostCommand implements Command {

    private final UserService userService = new UserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        AddBalanceForm form = new AddBalanceForm();
        form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        form.mapRequestToForm(request);
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/profile/balance", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        userService.addUserBalance(form, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/profile/balance", request), RequestDirection.REDIRECT);
        }
        return new CommandResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
    }
}
