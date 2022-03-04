package dao.dao.impl;

import dao.dao.UserDao;
import db.ConnectionPool;
import forms.RegisterForm;
import models.User;
import models.base.PreparedStatementMapper;
import models.base.SqlMapper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgreSQLUserDao extends UserDao {

    private final static String SELECT_USER_BY_ID = "select * from users where id = ?";
    private final static String SELECT_ALL_USERS = "select * from users";
    private final static String INSERT_USER = "insert into users(login, email) values (?, ?)";

    @Override
    public User getUserById(int id) {
        try(Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(SELECT_USER_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            User user = new User();
            while (rs.next()){
                SqlMapper<User> userMapper = new SqlMapper<>(user);
                userMapper.mapFromResultSet(rs);
            }
            return user;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            //TODO do not return null
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Connection connection = ConnectionPool.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS);
            List<User> users = new LinkedList<>();
            while (rs.next()){
                User user = new User();
                SqlMapper<User> userMapper = new SqlMapper<>(user);
                userMapper.mapFromResultSet(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            //TODO do not return null
            return null;
        }
    }

    @Override
    public boolean createUser(RegisterForm form) {
        try(Connection connection = ConnectionPool.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(INSERT_USER);
            PreparedStatementMapper<RegisterForm> stmtMapper = new PreparedStatementMapper<>(form, stmt, "password");
            stmtMapper.mapToPreparedStatement();
            return stmt.executeUpdate() == 1;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

}
