package commands.impl;

import commands.base.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Room;
import models.base.pagination.Pageable;
import service.RoomsService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;

@WebMapping(url = "", method = RequestMethod.GET)
public class MainCommand implements Command {
    private final RoomsService roomsService = new RoomsService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        //TODO add config for entities per page
        Pageable pageable = Pageable.of(request, 10 + 1);
        List<Room> rooms = roomsService.getAllRooms(getLocaleFromCookies(request.getCookies()), pageable);
        request.setAttribute("rooms", rooms);
        return new CommandResult("/index.jsp", RequestDirection.FORWARD);
    }
}
