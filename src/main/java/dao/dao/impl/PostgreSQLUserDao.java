package dao.dao.impl;

import dao.dao.UserDao;
import db.ConnectionPool;
import models.User;
import models.base.SqlMapper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgreSQLUserDao extends UserDao {

    private final static String SELECT_USER_BY_ID = "select * from users where id = ?";
    private final static String SELECT_ALL_USERS = "select * from users";

    @Override
    public User getUserById(int id) {
        try(Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(SELECT_USER_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            User user = new User();
            while (rs.next()){
//                user.setId(rs.getInt("id"));
//                user.setLogin(rs.getString("login"));
//                user.setEmail(rs.getString("email"));
                SqlMapper<User> userMapper = new SqlMapper<>(user);
                userMapper.mapFromResultSet(rs);
            }
            return user;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
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
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
            return users;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }

}
