package commands.impl;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.Security;
import dao.dao.BillingDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import service.BillingService;

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
        boolean billingPaid = billingService.payBilling(userId, billingId);
        if(!billingPaid){
            return new CommandResult(getAbsoluteUrl("/profile/my-billings", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(getAbsoluteUrl("/profile/room-history", request), RequestDirection.REDIRECT);
        }
    }
}
