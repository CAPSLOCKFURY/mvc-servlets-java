package controllers;

import forms.AddBalanceForm;
import forms.UserUpdateProfileForm;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.RoomRequest;
import models.User;
import models.base.pagination.Pageable;
import models.dto.RoomHistoryDTO;
import service.RoomRequestService;
import service.RoomsService;
import service.UserService;
import web.base.RequestDirection;
import web.base.RequestMethod;
import web.base.WebResult;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;
import web.base.messages.CookieMessageTransport;
import web.base.messages.MessageTransport;
import web.base.security.AuthenticatedOnly;
import web.base.security.Security;

import java.util.List;
import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class ProfileController {

    private final UserService userService = UserService.getInstance();

    private final RoomsService roomsService = RoomsService.getInstance();

    private final RoomRequestService roomRequestService = RoomRequestService.getInstance();

    @WebMapping(url = "/profile", method = RequestMethod.GET)
    public WebResult getProfile(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)) {
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        Long userId;
        User user = (User) session.getAttribute("user");
        try{
            userId = user.getId();
        } catch (NumberFormatException nfe){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        User dbUser = userService.getUserById(userId);
        request.setAttribute("user", dbUser);
        return new WebResult("profile.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/profile/update", method = RequestMethod.GET)
    public WebResult showUpdateProfile(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        User sessionUser = (User) request.getSession().getAttribute("user");
        User user = userService.getUserById(sessionUser.getId());
        request.setAttribute("dbUser", user);
        return new WebResult("profile-update.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/profile/update", method = RequestMethod.POST)
    public WebResult updateProfile(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        UserUpdateProfileForm form = new UserUpdateProfileForm();
        form.mapRequestToForm(request);
        if(!form.validate()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/profile/update", request), RequestDirection.REDIRECT);
        }
        User user = (User) request.getSession().getAttribute("user");
        boolean isUpdated = userService.updateUser(form, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/profile/update", request), RequestDirection.REDIRECT);
        }
        if(isUpdated){
            return new WebResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
        } else {
            return new WebResult(getAbsoluteUrl("/profile/update", request), RequestDirection.REDIRECT);
        }
    }

    @WebMapping(url = "/profile/balance", method = RequestMethod.POST)
    public WebResult addBalance(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        AddBalanceForm form = new AddBalanceForm();
        form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        form.mapRequestToForm(request);
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/profile/balance", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        userService.addUserBalance(form, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult(getAbsoluteUrl("/profile/balance", request), RequestDirection.REDIRECT);
        }
        return new WebResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/profile/room-history", method = RequestMethod.GET)
    public WebResult getRoomHistory(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Pageable pageable = Pageable.of(request, 10, true);
        List<RoomHistoryDTO> roomHistory = roomsService.getUserRoomHistory(user.getId(), getLocaleFromCookies(request.getCookies()), pageable);
        request.setAttribute("rooms", roomHistory);
        return new WebResult("user-room-history.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/profile/my-room-requests", method = RequestMethod.GET)
    public WebResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.processMessages(request, response);
        Pageable pageable = Pageable.of(request, 10, true);
        List<RoomRequest> roomRequestList = roomRequestService.getRoomRequestsByUserId(user.getId(), getLocaleFromCookies(request.getCookies()), pageable);
        request.setAttribute("roomRequests", roomRequestList);
        return new WebResult("user-room-requests.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/change-language", method = RequestMethod.GET)
    public WebResult changeLanguage(HttpServletRequest request, HttpServletResponse response){
        Cookie langCookie = new Cookie("Content-Language", request.getParameter("lang"));
        langCookie.setMaxAge(-1);
        response.addCookie(langCookie);
        return new WebResult(request.getHeader("referer"), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/profile/balance", method = RequestMethod.GET)
    public WebResult showProfileBalance(HttpServletRequest request, HttpServletResponse response){
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        return new WebResult("balance.jsp", RequestDirection.FORWARD);
    }

}
