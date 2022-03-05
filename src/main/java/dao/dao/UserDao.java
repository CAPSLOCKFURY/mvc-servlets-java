package dao.dao;

import forms.LoginForm;
import forms.RegisterForm;
import models.User;

import java.sql.SQLException;
import java.util.List;

public abstract class UserDao extends AbstractDao {
    public abstract User getUserById(int id) throws SQLException;

    public abstract List<User> getAllUsers() throws SQLException;

    public abstract long createUser(RegisterForm form) throws SQLException;

    public abstract User getUserByEmail(String email) throws SQLException;

    public abstract User getUserByLogin(String login) throws SQLException;

    public abstract User getUserByLoginAndPassword(LoginForm form) throws SQLException;
}
