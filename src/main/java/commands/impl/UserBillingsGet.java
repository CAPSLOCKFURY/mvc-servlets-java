package commands.impl;

import commands.base.*;
import commands.base.messages.CookieMessageTransport;
import commands.base.messages.MessageTransport;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Billing;
import models.User;
import service.BillingService;

import java.util.List;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/my-billings", method = RequestMethod.GET)
public class UserBillingsGet implements Command {

    private final BillingService billingService = new BillingService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.processMessages(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Billing> billings = billingService.findBillingsByUserId(user.getId());
        request.setAttribute("billings", billings);
        return new CommandResult("user-billings.jsp", RequestDirection.FORWARD);
    }
}
