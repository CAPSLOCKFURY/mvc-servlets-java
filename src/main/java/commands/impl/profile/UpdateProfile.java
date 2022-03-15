package commands.impl.profile;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.UserService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/update", method = RequestMethod.GET)
public class UpdateProfile implements Command {

    private final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        User sessionUser = (User) request.getSession().getAttribute("user");
        User user = userService.getUserById(sessionUser.getId());
        request.setAttribute("dbUser", user);
        return new CommandResult("profile-update.jsp", RequestDirection.FORWARD);
    }
}
