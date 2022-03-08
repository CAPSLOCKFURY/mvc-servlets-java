package commands.impl;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.UserService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile", method = RequestMethod.GET)
public class ProfileGetCommand implements Command {

    private final UserService userService = new UserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)) {
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        //TODO probably add method for getting user id from session
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
