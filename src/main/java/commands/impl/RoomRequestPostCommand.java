package commands.impl;

import commands.base.*;
import forms.RoomRequestForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/room-request", method = RequestMethod.POST)
public class RoomRequestPostCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RoomRequestForm form = new RoomRequestForm();
        form.mapRequestToForm(request);
        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
    }
}
