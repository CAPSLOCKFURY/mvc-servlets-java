package utils.testfolders.case3.inner;

import web.base.Command;
import web.base.CommandResult;
import web.base.RequestMethod;
import web.base.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "", method = RequestMethod.GET)
public class DCommandCase3 implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
