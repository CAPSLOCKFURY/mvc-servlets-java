package utils.testfolders.case3;

import web.base.Command;
import web.base.WebResult;
import web.base.RequestMethod;
import web.base.annotations.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "/123/123", method = RequestMethod.POST)
public class BCommandCase3 implements Command {
    @Override
    public WebResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
