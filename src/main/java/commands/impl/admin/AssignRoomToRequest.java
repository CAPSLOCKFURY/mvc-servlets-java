package commands.impl.admin;

import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminRoomsService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/room-request/assign", method = RequestMethod.POST)
public class AssignRoomToRequest implements Command {

    private final AdminRoomsService roomsService = AdminRoomsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long roomId;
        Long requestId;
        try {
            roomId = Long.parseLong(request.getParameter("roomId"));
            requestId = Long.parseLong(request.getParameter("requestId"));
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        boolean assigned = roomsService.assignRoomToRequest(roomId, requestId);
        if(assigned){
            return new CommandResult(getAbsoluteUrl("/admin/room-requests", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(request.getHeader("referer"), RequestDirection.REDIRECT);
        }
    }
}
