package commands.impl;

import commands.base.*;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import db.ConnectionPool;
import db.PooledConnection;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebMapping(url = "", method = RequestMethod.GET)
public class MainCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
//        List<User> users = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao().getAllUsers();
//        request.setAttribute("users", users);
        return new CommandResult("/index.jsp", RequestDirection.FORWARD);
    }
}
