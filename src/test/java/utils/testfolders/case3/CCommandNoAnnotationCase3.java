package utils.testfolders.case3;

import commands.base.Command;
import commands.base.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CCommandNoAnnotationCase3 implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
