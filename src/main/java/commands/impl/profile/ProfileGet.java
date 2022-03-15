package commands.impl.profile;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.UserService;

import static utils.UrlUtils.getAbsoluteUrl;
//TODO add ability to change password
@WebMapping(url = "/profile", method = RequestMethod.GET)
public class ProfileGet implements Command {

    private final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)) {
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        Long userId;
        User user = (User) session.getAttribute("user");
        try{
            userId = user.getId();
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        User dbUser = userService.getUserById(userId);
        request.setAttribute("user", dbUser);
        return new CommandResult("profile.jsp", RequestDirection.FORWARD);
    }
}
