package utils.testfolders.case1;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.RequestMethod;
import commands.base.WebMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebMapping(url = "", method = RequestMethod.POST)
public class ACommandCase1 implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
