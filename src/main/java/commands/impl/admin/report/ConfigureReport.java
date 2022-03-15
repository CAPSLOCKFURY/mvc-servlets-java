package commands.impl.admin.report;

import commands.base.*;
import commands.base.security.ManagerOnly;
import commands.base.security.Security;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/admin/report", method = RequestMethod.GET)
public class ConfigureReport implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Security security = new ManagerOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        return new CommandResult("/admin/configure-report.jsp", RequestDirection.FORWARD);
    }
}
