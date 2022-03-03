package commands.impl;

import commands.base.*;
import db.ConnectionPool;
import db.PooledConnection;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebMapping(url = "", method = RequestMethod.GET)
public class MainCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult("/index.jsp", RequestDirection.FORWARD);
    }
}
