package controllers;

import forms.RoomRequestForm;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.RoomClass;
import models.User;
import service.RoomRequestService;
import service.RoomsService;
import web.base.RequestDirection;
import web.base.RequestMethod;
import web.base.WebResult;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;
import web.base.messages.CookieMessageTransport;
import web.base.messages.MessageTransport;
import web.base.security.AuthenticatedOnly;
import web.base.security.Security;
import web.resolvers.annotations.Form;
import web.resolvers.annotations.GetParameter;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class RoomRequestController {

    private final RoomRequestService roomRequestService = RoomRequestService.getInstance();
    private final RoomsService roomsService = RoomsService.getInstance();

    @WebMapping(url = "/profile/my-room-requests/confirm", method = RequestMethod.POST)
    public WebResult confirmRoomRequest(HttpServletRequest request, HttpServletResponse response,
                                        @GetParameter(required = true, value = "requestId") Long requestId) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        boolean isConfirmed = roomRequestService.confirmRoomRequest(requestId, ((User)request.getSession().getAttribute("user")).getId(), messageTransport);
        messageTransport.setMessage(request, response);
        if(isConfirmed) {
            return new WebResult(getAbsoluteUrl("/profile/my-billings", request), RequestDirection.REDIRECT);
        } else {
            return new WebResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
        }
    }

    @WebMapping(url = "/profile/my-room-requests/decline", method = RequestMethod.POST)
    public WebResult declineAssignedRoom(HttpServletRequest request, HttpServletResponse response,
                                         @GetParameter(required = true, value = "requestId") Long requestId,
                                         @GetParameter(required = true, value = "comment") String comment, User user) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        roomRequestService.declineAssignedRoom(comment, user.getId(), requestId, messageTransport);
        messageTransport.setMessage(request, response);
        return new WebResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/profile/my-room-requests/disable", method = RequestMethod.GET)
    public WebResult disableRoomRequest(HttpServletRequest request, HttpServletResponse response,
                                        @GetParameter("id") Long id, User user) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long userId = user.getId();
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        roomRequestService.disableRoomRequest(id, userId, messageTransport);
        messageTransport.setMessage(request, response);
        return new WebResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/room-request", method = RequestMethod.POST)
    public WebResult createRoomRequest(HttpServletRequest request, HttpServletResponse response,
                                       @Form(RoomRequestForm.class)RoomRequestForm form, User user) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/room-request", request), RequestDirection.REDIRECT);
        }
        Long userId = user.getId();
        form.setUserId(userId);
        roomRequestService.createRoomRequest(form);
        return new WebResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/room-request", method = RequestMethod.GET)
    public WebResult showCreateRoomRequest(HttpServletRequest request, HttpServletResponse response){
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        List<RoomClass> roomClasses = roomsService.getRoomClasses(getLocaleFromCookies(request.getCookies()));
        Map<String, String> options = roomClasses.stream().collect(Collectors.toMap(c -> String.valueOf(c.getId()), RoomClass::getName));
        request.setAttribute("roomClassesMap", options);
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        return new WebResult("room-request.jsp", RequestDirection.FORWARD);
    }

}
