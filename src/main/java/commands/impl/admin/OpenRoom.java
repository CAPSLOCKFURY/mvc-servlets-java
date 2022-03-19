package commands.impl.admin;

import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminRoomsService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/room/open", method = RequestMethod.POST)
public class OpenRoom implements Command {

    private final AdminRoomsService roomsService = AdminRoomsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long roomId;
        try{
            roomId = Long.parseLong(request.getParameter("roomId"));
        } catch (IllegalArgumentException iag){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        roomsService.openRoom(roomId);
        return new CommandResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
    }
}
