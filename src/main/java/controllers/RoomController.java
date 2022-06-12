package controllers;

import forms.BookRoomForm;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Room;
import models.User;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.RoomExtendedInfo;
import models.enums.RoomOrdering;
import service.RoomsService;
import web.base.RequestDirection;
import web.base.RequestMethod;
import web.base.WebResult;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;
import web.base.security.AuthenticatedOnly;
import web.base.security.Security;
import web.resolvers.annotations.Form;
import web.resolvers.annotations.GetParameter;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class RoomController {

    private final RoomsService roomsService = RoomsService.getInstance();

    @WebMapping(url = "", method = RequestMethod.GET)
    public WebResult listRooms(HttpServletRequest request) {
        Pageable pageable = Pageable.of(request, 10, true);
        RoomOrdering roomOrdering = RoomOrdering.valueOfOrDefault(request.getParameter("orderColName"));
        OrderDirection orderDirection = OrderDirection.valueOfOrDefault(request.getParameter("orderDirection"));
        List<Room> rooms = roomsService.getAllRooms(getLocaleFromCookies(request.getCookies()), new Orderable(roomOrdering.getColName(), orderDirection), pageable);
        request.setAttribute("rooms", rooms);
        return new WebResult("/index.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/room", method = RequestMethod.GET)
    public WebResult getRoom(HttpServletRequest request, HttpServletResponse response, @GetParameter(value = "id", required = true) Long roomId) {
        FormErrorPRG errorsProcessor = new CookieFormErrorsPRG();
        errorsProcessor.processErrors(request, response);
        RoomExtendedInfo room = roomsService.getExtendedRoomInfo(roomId, getLocaleFromCookies(request.getCookies()));
        request.setAttribute("room", room);
        return new WebResult("room.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/room", method = RequestMethod.POST)
    public WebResult bookRoom(HttpServletRequest request, HttpServletResponse response,
                              @GetParameter(value = "id", required = true) Long roomId,
                              @Form(BookRoomForm.class) BookRoomForm form, User user) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
        }
        roomsService.bookRoom(form, roomId, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
        }
        return new WebResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
    }
}
