package commands.impl.admin;

import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Room;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomOrdering;
import service.AdminRoomRequestService;
import service.AdminRoomsService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/room-request", method = RequestMethod.GET)
public class RoomRequestGet implements Command {

    private final AdminRoomRequestService roomRequestService = AdminRoomRequestService.getInstance();
    private final AdminRoomsService roomsService = AdminRoomsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long requestId;
        try{
            requestId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
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
        return new CommandResult("/admin/admin-room-request.jsp", RequestDirection.FORWARD);
    }
}
