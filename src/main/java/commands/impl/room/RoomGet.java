package commands.impl.room;

import commands.base.*;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.dto.RoomExtendedInfo;
import service.RoomsService;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/room", method = RequestMethod.GET)
public class RoomGet implements Command {

    private final RoomsService roomsService = new RoomsService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Long roomId = null;
        try {
            roomId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG errorsProcessor = new CookieFormErrorsPRG();
        errorsProcessor.processErrors(request, response);
        RoomExtendedInfo room = roomsService.getExtendedRoomInfo(roomId, getLocaleFromCookies(request.getCookies()));
        request.setAttribute("room", room);
        return new CommandResult("room.jsp", RequestDirection.FORWARD);
    }
}
