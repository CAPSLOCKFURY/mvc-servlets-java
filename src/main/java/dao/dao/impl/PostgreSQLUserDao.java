package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.UserDao;
import db.ConnectionPool;
import forms.AddBalanceForm;
import forms.LoginForm;
import forms.RegisterForm;
import forms.UserUpdateProfileForm;
import models.User;
import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLUserDao extends UserDao {

    @Override
    public User getUserById(Long id) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
          return getOneById(connection, SqlQueries.User.FIND_USER_BY_ID, id, User.class);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAll(connection, SqlQueries.User.FIND_ALL_USERS, User.class);
        }
    }

    @Override
    public long createUser(RegisterForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntityAndGetId(connection, SqlQueries.User.INSERT_USER, form);
        }
    }

    @Override
    public User getUserByEmail(String email) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_EMAIL, new Object[]{email}, User.class);
        }
    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_LOGIN, new Object[]{login}, User.class);
        }
    }

    @Override
    public User getUserByLoginAndPassword(LoginForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_LOGIN_AND_PASSWORD, form, User.class);
        }
    }

    @Override
    public boolean addUserBalance(AddBalanceForm form, Long userId) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, SqlQueries.User.ADD_USER_BALANCE, form, userId);
        }
    }

    @Override
    public boolean updateUser(UserUpdateProfileForm form, Long userId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, SqlQueries.User.UPDATE_USER, form, userId);
        }
    }

    public User findUserForPasswordChange(String password, Long userId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_USER_FOR_PASSWORD_CHANGE, new Object[]{password, userId}, User.class);
        }
    }

    @Override
    public boolean changePassword(String newPassword, Long userId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntity(connection, SqlQueries.User.CHANGE_PASSWORD, new Object[]{newPassword, userId});
        }
    }

}
