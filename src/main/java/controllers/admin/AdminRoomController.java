package controllers.admin;

import forms.CloseRoomForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminRoomsService;
import web.base.*;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;
import web.base.security.ManagerOnly;
import web.base.security.Security;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class AdminRoomController {

    private final AdminRoomsService roomService = AdminRoomsService.getInstance();

    @WebMapping(url = "/admin/room/close", method = RequestMethod.POST)
    public WebResult closeRoom(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if (!security.doSecurity(request, response)) {
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        CloseRoomForm form = new CloseRoomForm();
        form.mapRequestToForm(request);
        Long roomId = null;
        try {
            roomId = Long.parseLong(request.getParameter("id"));
        } catch (IllegalArgumentException iag) {
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
        }
        roomService.closeRoom(roomId, form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        return new WebResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/admin/room/open", method = RequestMethod.POST)
    public WebResult openRoom(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long roomId;
        try{
            roomId = Long.parseLong(request.getParameter("roomId"));
        } catch (IllegalArgumentException iag){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        roomService.openRoom(roomId);
        return new WebResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
    }

}
