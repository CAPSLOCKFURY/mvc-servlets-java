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
import web.base.security.annotations.AuthenticatedOnly;
import web.resolvers.annotations.GetParameter;

import java.util.List;
import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;

@WebController
public class BillingController {

    private final BillingService billingService = BillingService.getInstance();

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/my-billings/pay", method = RequestMethod.POST)
    public WebResult payBilling(HttpServletRequest request, HttpServletResponse response,
                                @GetParameter(required = true, value = "billingId") Long billingId, User user) {
        Long userId = user.getId();
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        boolean billingPaid = billingService.payBilling(userId, billingId, messageTransport);
        messageTransport.setMessage(request, response);
        if(!billingPaid){
            return new WebResult("/profile/my-billings", RequestDirection.REDIRECT);
        } else {
            return new WebResult("/profile/room-history", RequestDirection.REDIRECT);
        }
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/my-billings", method = RequestMethod.GET)
    public WebResult getUserBillings(HttpServletRequest request, HttpServletResponse response, User user) {
        MessageTransport messageTransport = new CookieMessageTransport();
        messageTransport.processMessages(request, response);
        HttpSession session = request.getSession();
        Pageable pageable = Pageable.of(request, 10, true);
        List<Billing> billings = billingService.findBillingsByUserId(user.getId(), pageable);
        request.setAttribute("billings", billings);
        return new WebResult("user-billings.jsp", RequestDirection.FORWARD);
    }

}
