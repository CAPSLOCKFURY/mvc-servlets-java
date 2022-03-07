package commands.impl;

import commands.base.*;
import dao.dao.RoomsDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Room;
import models.dto.RoomExtendedInfo;
import service.RoomsService;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;

@WebMapping(url = "", method = RequestMethod.GET)
public class MainCommand implements Command {
    private final RoomsService roomsService = new RoomsService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<Room> rooms = roomsService.getAllRooms(getLocaleFromCookies(request.getCookies()));
        request.setAttribute("rooms", rooms);
        return new CommandResult("/index.jsp", RequestDirection.FORWARD);
    }
}
