package commands.impl.roomrequest;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import forms.RoomRequestForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.RoomRequestService;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/room-request", method = RequestMethod.POST)
public class RoomRequestPost implements Command {

    private final RoomRequestService roomRequestService = new RoomRequestService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        RoomRequestForm form = new RoomRequestForm();
        form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        form.mapRequestToForm(request);
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/room-request", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        form.setUserId(userId);
        roomRequestService.createRoomRequest(form);
        return new CommandResult(getAbsoluteUrl("/profile/my-room-requests", request), RequestDirection.REDIRECT);
    }
}
