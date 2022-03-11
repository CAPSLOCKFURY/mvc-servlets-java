package commands.impl.room;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import forms.BookRoomForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.RoomsService;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/room", method = RequestMethod.POST)
public class BookRoomPostCommand implements Command {

    private final RoomsService roomsService = new RoomsService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        BookRoomForm form = new BookRoomForm();
        form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        form.mapRequestToForm(request);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long roomId;
        try{
            roomId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        boolean isValid = form.validate();
        if(!isValid){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
        }
        roomsService.bookRoom(form, roomId, user.getId());
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
        }
        return new CommandResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
    }
}
