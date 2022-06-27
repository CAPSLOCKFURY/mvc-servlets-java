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
import web.base.security.annotations.AuthenticatedOnly;
import web.resolvers.annotations.Form;

import java.util.List;

import static utils.LocaleUtils.getLocaleFromCookies;

@WebController
public class ProfileController {

    private final UserService userService = UserService.getInstance();

    private final RoomsService roomsService = RoomsService.getInstance();

    private final RoomRequestService roomRequestService = RoomRequestService.getInstance();

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile", method = RequestMethod.GET)
    public WebResult getProfile(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        Long userId = user.getId();
        User dbUser = userService.getUserById(userId);
        request.setAttribute("user", dbUser);
        return new WebResult("profile.jsp", RequestDirection.FORWARD);
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/update", method = RequestMethod.GET)
    public WebResult showUpdateProfile(HttpServletRequest request, HttpServletResponse response, User sessionUser) {
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        User user = userService.getUserById(sessionUser.getId());
        request.setAttribute("dbUser", user);
        return new WebResult("profile-update.jsp", RequestDirection.FORWARD);
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/update", method = RequestMethod.POST)
    public WebResult updateProfile(HttpServletRequest request, HttpServletResponse response,
                                   @Form UserUpdateProfileForm form) {
        if(!form.validate()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/profile/update", RequestDirection.REDIRECT);
        }
        User user = (User) request.getSession().getAttribute("user");
        boolean isUpdated = userService.updateUser(form, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/profile/update", RequestDirection.REDIRECT);
        }
        if(isUpdated){
            return new WebResult("/profile", RequestDirection.REDIRECT);
        } else {
            return new WebResult("/profile/update", RequestDirection.REDIRECT);
        }
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/balance", method = RequestMethod.POST)
    public WebResult addBalance(HttpServletResponse response,
                                @Form AddBalanceForm form, User user) {
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/profile/balance", RequestDirection.REDIRECT);
        }
        userService.addUserBalance(form, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/profile/balance", RequestDirection.REDIRECT);
        }
        return new WebResult("/profile", RequestDirection.REDIRECT);
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/room-history", method = RequestMethod.GET)
    public WebResult getRoomHistory(HttpServletRequest request, User user) {
        Pageable pageable = Pageable.of(request, 10, true);
        List<RoomHistoryDTO> roomHistory = roomsService.getUserRoomHistory(user.getId(), getLocaleFromCookies(request.getCookies()), pageable);
        request.setAttribute("rooms", roomHistory);
        return new WebResult("user-room-history.jsp", RequestDirection.FORWARD);
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/my-room-requests", method = RequestMethod.GET)
    public WebResult getMyRoomRequests(HttpServletRequest request, HttpServletResponse response, User user) {
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

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/balance", method = RequestMethod.GET)
    public WebResult showProfileBalance(HttpServletRequest request, HttpServletResponse response){
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        return new WebResult("balance.jsp", RequestDirection.FORWARD);
    }

}
