package dao.dao.impl;

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

    private final static String FIND_USER_BY_ID = "select * from users where id = ?";
    private final static String FIND_ALL_USERS = "select * from users";
    private final static String INSERT_USER = "insert into users(login, email, password, first_name, last_name) values (?, ?, md5(?), ?, ?)";
    private final static String FIND_BY_LOGIN = "select * from users where login = ?";
    private final static String FIND_BY_EMAIL = "select * from users where email = ?";
    private final static String FIND_BY_LOGIN_AND_PASSWORD = "select * from users where login = ? and password = md5(?)";
    private final static String ADD_USER_BALANCE = "update users set balance = balance + ? where id = ?";
    private final static String UPDATE_USER = "update users set first_name = ?, last_name = ? where id = ?";

    @Override
    public User getUserById(Long id) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
          return getOneById(connection, FIND_USER_BY_ID, id, User.class);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAll(connection, FIND_ALL_USERS, User.class);
        }
    }

    @Override
    public long createUser(RegisterForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntityAndGetId(connection, INSERT_USER, form);
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
            return getOneByParams(connection, FIND_BY_EMAIL, new EmailParam(email), User.class);
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
            return getOneByParams(connection, FIND_BY_LOGIN, new LoginParam(login), User.class);
        }
    }

    @Override
    public User getUserByLoginAndPassword(LoginForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, FIND_BY_LOGIN_AND_PASSWORD, form, User.class);
        }
    }

    @Override
    public boolean addUserBalance(AddBalanceForm form, Long userId) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, ADD_USER_BALANCE, form, userId);
        }
    }

    @Override
    public boolean updateUser(UserUpdateProfileForm form, Long userId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, UPDATE_USER, form, userId);
        }
    }

}
