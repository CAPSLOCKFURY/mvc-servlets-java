package commands.impl.profile;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import models.dto.RoomHistoryDTO;
import service.RoomsService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/room-history", method = RequestMethod.GET)
public class RoomHistoryGet implements Command {

    private final RoomsService roomsService = new RoomsService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<RoomHistoryDTO> roomHistory = roomsService.getUserRoomHistory(user.getId(), getLocaleFromCookies(request.getCookies()));
        request.setAttribute("rooms", roomHistory);
        return new CommandResult("user-room-history.jsp", RequestDirection.FORWARD);
    }
}
