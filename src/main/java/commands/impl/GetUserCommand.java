package commands.impl;

import commands.base.*;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

@WebMapping(url = "/user", method = RequestMethod.GET)
public class GetUserCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = DaoAbstractFactory.getFactory(SqlDB.MYSQL).getUserDao().getUserById(id);
        request.setAttribute("user", user);
        return new CommandResult("/user.jsp", RequestDirection.FORWARD);
    }
}
