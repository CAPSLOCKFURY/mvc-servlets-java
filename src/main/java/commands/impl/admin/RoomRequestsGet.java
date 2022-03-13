package commands.impl.admin;


import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomRequestStatus;
import service.AdminRoomRequestService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/room-requests", method = RequestMethod.GET)
public class RoomRequestsGet implements Command {

    private final AdminRoomRequestService roomRequestService = new AdminRoomRequestService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        RoomRequestStatus requestStatusFilter = RoomRequestStatus.AWAITING;
        if(request.getParameter("requestStatus") != null){
            requestStatusFilter = RoomRequestStatus.valueOf(request.getParameter("requestStatus"));
        }
        Pageable pageable = Pageable.of(request, 10, true);
        List<AdminRoomRequestDTO> requests = roomRequestService.getAdminRoomRequests(getLocaleFromCookies(request.getCookies()), requestStatusFilter, pageable);
        request.setAttribute("roomRequests", requests);
        return new CommandResult("/admin/admin-room-requests.jsp", RequestDirection.FORWARD);
    }
}
