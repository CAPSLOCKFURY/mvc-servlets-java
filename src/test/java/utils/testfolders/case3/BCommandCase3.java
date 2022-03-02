package utils.testfolders.case3;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.RequestMethod;
import commands.base.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "/123/123", method = RequestMethod.POST)
public class BCommandCase3 implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
