package utils.testfolders.case1;

import web.base.Command;
import web.base.CommandResult;
import web.base.RequestMethod;
import web.base.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "", method = RequestMethod.POST)
public class ACommandCase1 implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
