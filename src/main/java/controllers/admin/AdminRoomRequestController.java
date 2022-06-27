package controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import models.Room;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomOrdering;
import models.enums.RoomRequestOrdering;
import models.enums.RoomRequestStatus;
import service.AdminRoomRequestService;
import service.AdminRoomsService;
import web.base.RequestDirection;
import web.base.RequestMethod;
import web.base.WebResult;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;
import web.base.security.annotations.ManagerOnly;
import web.resolvers.annotations.GetParameter;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;

@WebController
public class AdminRoomRequestController {

    private final AdminRoomsService roomsService = AdminRoomsService.getInstance();
    private final AdminRoomRequestService roomRequestService = AdminRoomRequestService.getInstance();

    @ManagerOnly("")
    @WebMapping(url = "/admin/room-request/assign", method = RequestMethod.POST)
    public WebResult assignRoomToRequest(HttpServletRequest request,
                                         @GetParameter(required = true, value = "roomId") Long roomId,
                                         @GetParameter(required = true, value = "requestId") Long requestId) {
        boolean assigned = roomsService.assignRoomToRequest(roomId, requestId);
        if(assigned){
            return new WebResult("/admin/room-requests", RequestDirection.REDIRECT);
        } else {
            return new WebResult(request.getHeader("referer"), RequestDirection.REDIRECT, true);
        }
    }

    @ManagerOnly("")
    @WebMapping(url = "/admin/room-requests", method = RequestMethod.GET)
    public WebResult listRoomRequests(HttpServletRequest request) {
        RoomRequestOrdering requestOrdering = RoomRequestOrdering.valueOfOrDefault(request.getParameter("requestOrdering"));
        OrderDirection orderDirection = OrderDirection.valueOfOrDefault(request.getParameter("orderDirection"));
        Orderable orderable = new Orderable(requestOrdering.getColName(), orderDirection);
        RoomRequestStatus requestStatusFilter = RoomRequestStatus.valueOfOrDefault(request.getParameter("requestStatus"));
        Pageable pageable = Pageable.of(request, 10, true);
        List<AdminRoomRequestDTO> requests = roomRequestService.getAdminRoomRequests(getLocaleFromCookies(request.getCookies()), requestStatusFilter, orderable, pageable);
        request.setAttribute("roomRequests", requests);
        return new WebResult("/admin/admin-room-requests.jsp", RequestDirection.FORWARD);
    }

    @ManagerOnly("")
    @WebMapping(url = "/admin/room-request", method = RequestMethod.GET)
    public WebResult getRoomRequest(HttpServletRequest request, @GetParameter(required = true, value = "id") Long requestId) {
        String locale = getLocaleFromCookies(request.getCookies());
        AdminRoomRequestDTO roomRequest = roomRequestService.getAdminRoomRequestById(requestId, locale);
        request.setAttribute("roomRequest", roomRequest);
        Pageable pageable = Pageable.of(request, 10, true);
        RoomOrdering roomOrdering = RoomOrdering.valueOfOrDefault(request.getParameter("orderColName"));
        OrderDirection orderDirection = OrderDirection.valueOfOrDefault(request.getParameter("orderDirection"));
        Orderable orderable = new Orderable(roomOrdering.getColName(), orderDirection);
        if(roomRequest.getStatus().equals("awaiting")) {
            List<Room> suitableRooms = roomsService.findSuitableRoomsForRequest(locale, roomRequest.getCheckInDate(), roomRequest.getCheckOutDate(), orderable, pageable);
            request.setAttribute("rooms", suitableRooms);
        }
        return new WebResult("/admin/admin-room-request.jsp", RequestDirection.FORWARD);
    }

    @ManagerOnly("")
    @WebMapping(url = "/admin/room-request/close", method = RequestMethod.POST)
    public WebResult closeRoomRequest(@GetParameter("managerComment") String managerComment,
                                      @GetParameter(required = true,value = "requestId") Long requestId) {
        roomRequestService.closeRoomRequest(requestId, managerComment);
        return new WebResult("/admin/room-requests", RequestDirection.REDIRECT);
    }

}
