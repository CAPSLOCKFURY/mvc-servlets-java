package commands.impl;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.RoomRequestService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/my-room-requests/confirm", method = RequestMethod.POST)
public class ConfirmRoomRequestCommand implements Command {

    private final RoomRequestService roomRequestService = new RoomRequestService();

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
        boolean isConfirmed = roomRequestService.confirmRoomRequest(requestId);
        if(isConfirmed) {
            return new CommandResult(getAbsoluteUrl("/profile/my-billings", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
        }
    }
}
