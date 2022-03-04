package dao.dao.impl;

import dao.dao.UserDao;
import db.ConnectionPool;
import exceptions.db.DaoException;
import forms.RegisterForm;
import models.User;

import java.sql.*;
import java.util.List;

public class PostgreSQLUserDao extends UserDao {

    private final static String SELECT_USER_BY_ID = "select * from users where id = ?";
    private final static String SELECT_ALL_USERS = "select * from users";
    private final static String INSERT_USER = "insert into users(login, email) values (?, ?)";

    @Override
    public User getUserById(int id) {
        try(Connection connection = ConnectionPool.getConnection()){
          return getOneById(connection, SELECT_USER_BY_ID, id, User.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAll(connection, SELECT_ALL_USERS, User.class);
        } catch (SQLException sqle){
            throw new DaoException();
        }
    }

    @Override
    public boolean createUser(RegisterForm form) {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntity(connection, INSERT_USER, form);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

}
