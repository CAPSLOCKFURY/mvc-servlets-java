package utils.testfolders.case3;

import web.base.Command;
import web.base.WebResult;
import web.base.RequestMethod;
import web.base.annotations.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "", method = RequestMethod.GET)
public class ACommandCase3 implements Command {
    @Override
    public WebResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
