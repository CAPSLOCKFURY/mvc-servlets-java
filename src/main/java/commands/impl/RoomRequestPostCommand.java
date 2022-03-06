package commands.impl;

import commands.base.*;
import forms.RoomRequestForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.RoomRequestService;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/room-request", method = RequestMethod.POST)
public class RoomRequestPostCommand implements Command {

    private final RoomRequestService roomRequestService = new RoomRequestService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RoomRequestForm form = new RoomRequestForm();
        form.mapRequestToForm(request);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Long userId = user.getId();
        form.setUserId(userId);
        roomRequestService.createRoomRequest(form);
        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
    }
}
