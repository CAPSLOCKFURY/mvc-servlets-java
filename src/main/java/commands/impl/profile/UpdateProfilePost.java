package commands.impl.profile;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import forms.UserUpdateProfileForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.UserService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/update", method = RequestMethod.POST)
public class UpdateProfilePost implements Command {

    private final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        UserUpdateProfileForm form = new UserUpdateProfileForm();
        form.mapRequestToForm(request);
        if(!form.validate()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/profile/update", request), RequestDirection.REDIRECT);
        }
        User user = (User) request.getSession().getAttribute("user");
        boolean isUpdated = userService.updateUser(form, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/profile/update", request), RequestDirection.REDIRECT);
        }
        if(isUpdated){
            return new CommandResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(getAbsoluteUrl("/profile/update", request), RequestDirection.REDIRECT);
        }
    }
}
