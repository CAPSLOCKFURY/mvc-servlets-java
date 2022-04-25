package utils.testfolders.case3.inner.inner;

import web.base.Command;
import web.base.CommandResult;
import web.base.RequestMethod;
import web.base.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "", method = RequestMethod.POST)
public class HCommandCase3 implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
