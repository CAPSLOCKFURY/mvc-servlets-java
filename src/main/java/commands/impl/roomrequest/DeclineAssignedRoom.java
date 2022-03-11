package commands.impl.roomrequest;

import commands.base.*;
import commands.base.messages.CookieMessageTransport;
import commands.base.messages.MessageTransport;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.RoomRequestService;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/my-room-requests/decline", method = RequestMethod.POST)
public class DeclineAssignedRoom implements Command {

    private final RoomRequestService roomRequestService = new RoomRequestService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long requestId;
        String comment;
        try{
            requestId = Long.parseLong(request.getParameter("requestId"));
            comment = request.getParameter("comment");
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        User user = (User)request.getSession().getAttribute("user");
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        roomRequestService.declineAssignedRoom(comment, user.getId(), requestId, messageTransport);
        return new CommandResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
    }
}
