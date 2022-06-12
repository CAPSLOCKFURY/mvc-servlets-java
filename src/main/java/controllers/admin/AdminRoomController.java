package controllers.admin;

import forms.CloseRoomForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminRoomsService;
import web.base.RequestDirection;
import web.base.RequestMethod;
import web.base.WebResult;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;
import web.base.security.ManagerOnly;
import web.base.security.Security;
import web.resolvers.annotations.Form;
import web.resolvers.annotations.GetParameter;


import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class AdminRoomController {

    private final AdminRoomsService roomService = AdminRoomsService.getInstance();

    @WebMapping(url = "/admin/room/close", method = RequestMethod.POST)
    public WebResult closeRoom(HttpServletRequest request, HttpServletResponse response,
                               @Form(CloseRoomForm.class) CloseRoomForm form, @GetParameter(required = true, value = "id") Long roomId) {
        Security security = new ManagerOnly();
        if (!security.doSecurity(request, response)) {
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
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
    public WebResult openRoom(HttpServletRequest request, HttpServletResponse response,
                              @GetParameter(required = true, value = "roomId") Long roomId) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        roomService.openRoom(roomId);
        return new WebResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
    }

}
