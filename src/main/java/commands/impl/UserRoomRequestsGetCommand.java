package commands.impl;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.RoomRequest;
import models.User;
import service.RoomRequestService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/my-room-requests", method = RequestMethod.GET)
public class UserRoomRequestsGetCommand implements Command {

    private final RoomRequestService roomRequestService = new RoomRequestService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<RoomRequest> roomRequestList = roomRequestService.getRoomRequestsByUserId(user.getId(), getLocaleFromCookies(request.getCookies()));
        request.setAttribute("roomRequests", roomRequestList);
        return new CommandResult("user-room-requests.jsp", RequestDirection.FORWARD);
    }
}
