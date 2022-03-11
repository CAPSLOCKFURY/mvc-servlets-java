package commands.impl.roomrequest;

import commands.base.*;
import commands.base.messages.CookieMessageTransport;
import commands.base.messages.MessageTransport;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.RoomRequestService;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/my-room-requests/disable", method = RequestMethod.GET)
public class DisableRoomRequest implements Command {

    private final RoomRequestService roomRequestService = new RoomRequestService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long id;
        Long userId;
        try {
            id = Long.parseLong(request.getParameter("id"));
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            userId = user.getId();
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        roomRequestService.disableRoomRequest(id, userId, messageTransport);
        messageTransport.setMessage(request, response);
        return new CommandResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
    }
}