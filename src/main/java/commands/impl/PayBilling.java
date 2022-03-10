package commands.impl;

import commands.base.*;
import commands.base.messages.CookieMessageTransport;
import commands.base.messages.MessageTransport;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.BillingService;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/profile/my-billings/pay", method = RequestMethod.POST)
public class PayBilling implements Command {

    private final BillingService billingService = new BillingService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long userId = ((User)request.getSession().getAttribute("user")).getId();
        Long billingId;
        try{
             billingId = Long.parseLong(request.getParameter("billingId"));
        } catch (NumberFormatException nfe){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        boolean billingPaid = billingService.payBilling(userId, billingId, messageTransport);
        response.addCookie(messageTransport.getMessageCookie(request, response));
        if(!billingPaid){
            return new CommandResult(getAbsoluteUrl("/profile/my-billings", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(getAbsoluteUrl("/profile/room-history", request), RequestDirection.REDIRECT);
        }
    }
}
