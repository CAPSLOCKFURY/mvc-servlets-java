package commands.impl.admin;


import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomRequestOrdering;
import models.enums.RoomRequestStatus;
import service.AdminRoomRequestService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/room-requests", method = RequestMethod.GET)
public class RoomRequestsGet implements Command {

    private final AdminRoomRequestService roomRequestService = AdminRoomRequestService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        RoomRequestOrdering requestOrdering = RoomRequestOrdering.valueOfOrDefault(request.getParameter("requestOrdering"));
        OrderDirection orderDirection = OrderDirection.valueOfOrDefault(request.getParameter("orderDirection"));
        Orderable orderable = new Orderable(requestOrdering.getColName(), orderDirection);
        RoomRequestStatus requestStatusFilter = RoomRequestStatus.valueOfOrDefault(request.getParameter("requestStatus"));
        Pageable pageable = Pageable.of(request, 10, true);
        List<AdminRoomRequestDTO> requests = roomRequestService.getAdminRoomRequests(getLocaleFromCookies(request.getCookies()), requestStatusFilter, orderable, pageable);
        request.setAttribute("roomRequests", requests);
        return new CommandResult("/admin/admin-room-requests.jsp", RequestDirection.FORWARD);
    }
}
