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
        class EmailParam{
            @SqlColumn(columnName = "email", type = SqlType.STRING)
            public String email;
            public EmailParam(String email) {
                this.email = email;
            }
            public String getEmail() {
                return email;
            }
            public void setEmail(String email) {
                this.email = email;
            }
        }
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_EMAIL, new EmailParam(email), User.class);
        }
    }

    @Override
    public User getUserByLogin(String login) throws SQLException {
        class LoginParam{
            @SqlColumn(columnName = "login", type = SqlType.STRING)
            public String login;
            public LoginParam(String login) {
                this.login = login;
            }
            public String getLogin() {
                return login;
            }
            public void setLogin(String login) {
                this.login = login;
            }
        }
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_LOGIN, new LoginParam(login), User.class);
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
        class Params{
            @SqlColumn(columnName = "", type = SqlType.STRING)
            private final String pass = password;
            @SqlColumn(columnName = "", type = SqlType.LONG)
            private final Long id = userId;
            public String getPass() {return pass;}
            public Long getId() {return id;}
        }
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_USER_FOR_PASSWORD_CHANGE, new Params(), User.class);
        }
    }

    @Override
    public boolean changePassword(String newPassword, Long userId) throws SQLException{
        class UpdateParams{
            @SqlColumn(columnName = "", type = SqlType.STRING)
            private final String password = newPassword;
            @SqlColumn(columnName = "", type = SqlType.LONG)
            private final Long id = userId;
            public String getPassword() {return password;}
            public Long getId() {return id;}
        }
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntity(connection, SqlQueries.User.CHANGE_PASSWORD, new UpdateParams());
        }
    }

}
