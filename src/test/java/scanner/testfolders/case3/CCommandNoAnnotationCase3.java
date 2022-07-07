package scanner.testfolders.case3;

import web.base.Command;
import web.base.WebResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CCommandNoAnnotationCase3 implements Command {
    @Override
    public WebResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
