package commands.impl.profile;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/change-password", method = RequestMethod.GET)
public class ChangePasswordGet implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG formErrorPRG = new CookieFormErrorsPRG();
        formErrorPRG.processErrors(request, response);
        return new CommandResult("change-password.jsp", RequestDirection.FORWARD);
    }
}
