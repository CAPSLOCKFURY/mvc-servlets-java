package controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import web.base.security.ManagerOnly;
import web.base.security.Security;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class AdminRoomRequestController {

    private final AdminRoomsService roomsService = AdminRoomsService.getInstance();
    private final AdminRoomRequestService roomRequestService = AdminRoomRequestService.getInstance();

    @WebMapping(url = "/admin/room-request/assign", method = RequestMethod.POST)
    public WebResult assignRoomToRequest(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long roomId;
        Long requestId;
        try {
            roomId = Long.parseLong(request.getParameter("roomId"));
            requestId = Long.parseLong(request.getParameter("requestId"));
        } catch (NumberFormatException nfe){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        boolean assigned = roomsService.assignRoomToRequest(roomId, requestId);
        if(assigned){
            return new WebResult(getAbsoluteUrl("/admin/room-requests", request), RequestDirection.REDIRECT);
        } else {
            return new WebResult(request.getHeader("referer"), RequestDirection.REDIRECT);
        }
    }

    @WebMapping(url = "/admin/room-requests", method = RequestMethod.GET)
    public WebResult listRoomRequests(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        RoomRequestOrdering requestOrdering = RoomRequestOrdering.valueOfOrDefault(request.getParameter("requestOrdering"));
        OrderDirection orderDirection = OrderDirection.valueOfOrDefault(request.getParameter("orderDirection"));
        Orderable orderable = new Orderable(requestOrdering.getColName(), orderDirection);
        RoomRequestStatus requestStatusFilter = RoomRequestStatus.valueOfOrDefault(request.getParameter("requestStatus"));
        Pageable pageable = Pageable.of(request, 10, true);
        List<AdminRoomRequestDTO> requests = roomRequestService.getAdminRoomRequests(getLocaleFromCookies(request.getCookies()), requestStatusFilter, orderable, pageable);
        request.setAttribute("roomRequests", requests);
        return new WebResult("/admin/admin-room-requests.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/admin/room-request", method = RequestMethod.GET)
    public WebResult getRoomRequest(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long requestId;
        try{
            requestId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException nfe){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
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

    @WebMapping(url = "/admin/room-request/close", method = RequestMethod.POST)
    public WebResult closeRoomRequest(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        String managerComment;
        Long requestId;
        try{
            managerComment = request.getParameter("managerComment");
            requestId = Long.parseLong(request.getParameter("requestId"));
        } catch (NumberFormatException nfe){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        roomRequestService.closeRoomRequest(requestId, managerComment);
        return new WebResult(getAbsoluteUrl("/admin/room-requests", request), RequestDirection.REDIRECT);
    }

}
