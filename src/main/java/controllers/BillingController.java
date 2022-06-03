package controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Billing;
import models.User;
import models.base.pagination.Pageable;
import service.BillingService;
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
public class BillingController {

    private final BillingService billingService = BillingService.getInstance();

    @WebMapping(url = "/profile/my-billings/pay", method = RequestMethod.POST)
    public WebResult payBilling(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        Long userId = ((User)request.getSession().getAttribute("user")).getId();
        Long billingId;
        try{
            billingId = Long.parseLong(request.getParameter("billingId"));
        } catch (NumberFormatException nfe){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        boolean billingPaid = billingService.payBilling(userId, billingId, messageTransport);
        messageTransport.setMessage(request, response);
        if(!billingPaid){
            return new WebResult(getAbsoluteUrl("/profile/my-billings", request), RequestDirection.REDIRECT);
        } else {
            return new WebResult(getAbsoluteUrl("/profile/room-history", request), RequestDirection.REDIRECT);
        }
    }

    @WebMapping(url = "/profile/my-billings", method = RequestMethod.GET)
    public WebResult getUserBillings(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new WebResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.processMessages(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Pageable pageable = Pageable.of(request, 10, true);
        List<Billing> billings = billingService.findBillingsByUserId(user.getId(), pageable);
        request.setAttribute("billings", billings);
        return new WebResult("user-billings.jsp", RequestDirection.FORWARD);
    }

}
