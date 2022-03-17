package commands.impl;

import commands.base.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Room;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.enums.RoomOrdering;
import service.RoomsService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;

@WebMapping(url = "", method = RequestMethod.GET)
public class MainCommand implements Command {
    private final RoomsService roomsService = RoomsService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Pageable pageable = Pageable.of(request, 10, true);
        RoomOrdering roomOrdering = RoomOrdering.valueOfOrDefault(request.getParameter("orderColName"));
        OrderDirection orderDirection = OrderDirection.valueOfOrDefault(request.getParameter("orderDirection"));
        List<Room> rooms = roomsService.getAllRooms(getLocaleFromCookies(request.getCookies()), new Orderable(roomOrdering.getColName(), orderDirection), pageable);
        request.setAttribute("rooms", rooms);
        return new CommandResult("/index.jsp", RequestDirection.FORWARD);
    }
}
