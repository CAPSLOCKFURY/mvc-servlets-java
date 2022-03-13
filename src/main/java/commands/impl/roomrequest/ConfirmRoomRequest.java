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

@WebMapping(url = "/profile/my-room-requests/confirm", method = RequestMethod.POST)
public class ConfirmRoomRequest implements Command {

    private final RoomRequestService roomRequestService = RoomRequestService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long requestId;
        try{
            requestId = Long.parseLong(request.getParameter("requestId"));
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        boolean isConfirmed = roomRequestService.confirmRoomRequest(requestId, ((User)request.getSession().getAttribute("user")).getId(), messageTransport);
        messageTransport.setMessage(request, response);
        if(isConfirmed) {
            return new CommandResult(getAbsoluteUrl("/profile/my-billings", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
        }
    }
}
