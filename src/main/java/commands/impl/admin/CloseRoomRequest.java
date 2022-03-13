package commands.impl.admin;

import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminRoomRequestService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/room-request/close", method = RequestMethod.POST)
public class CloseRoomRequest implements Command {

    private final AdminRoomRequestService roomRequestService = AdminRoomRequestService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        String managerComment;
        Long requestId;
        try{
            managerComment = request.getParameter("managerComment");
            requestId = Long.parseLong(request.getParameter("requestId"));
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        roomRequestService.closeRoomRequest(requestId, managerComment);
        return new CommandResult(getAbsoluteUrl("/admin/room-requests", request), RequestDirection.REDIRECT);
    }
}
