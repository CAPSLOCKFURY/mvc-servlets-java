package commands.impl;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.RequestMethod;
import commands.base.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BillingService;

@WebMapping(url = "/profile/my-billings", method = RequestMethod.GET)
public class UserBillingsGet implements Command {

    private final BillingService billingService = new BillingService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
