package scanner.testfolders.case3.inner;

import web.base.Command;
import web.base.WebResult;
import web.base.RequestMethod;
import web.base.annotations.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "", method = RequestMethod.GET)
public class DCommandCase3 implements Command {
    @Override
    public WebResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
