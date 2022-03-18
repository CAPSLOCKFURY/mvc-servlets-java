package commands.impl.admin;

import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import forms.CloseRoomForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminRoomsService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/room/close", method = RequestMethod.POST)
public class CloseRoom implements Command {

    private final AdminRoomsService roomService = AdminRoomsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if (!security.doSecurity(request, response)) {
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        CloseRoomForm form = new CloseRoomForm();
        form.mapRequestToForm(request);
        if(!form.validate()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        Long roomId = null;
        try {
            roomId = Long.parseLong(request.getParameter("id"));
        } catch (IllegalArgumentException iag) {
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        roomService.closeRoom(roomId, form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        return new CommandResult(getAbsoluteUrl("/room?id=" + roomId, request), RequestDirection.REDIRECT);
    }
}
