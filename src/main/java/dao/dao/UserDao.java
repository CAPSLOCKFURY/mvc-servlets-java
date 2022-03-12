package dao.dao;

import forms.AddBalanceForm;
import forms.LoginForm;
import forms.RegisterForm;
import models.User;

import java.sql.SQLException;
import java.util.List;

public abstract class UserDao extends OrderableAbstractDao {
    public abstract User getUserById(Long id) throws SQLException;

    public abstract List<User> getAllUsers() throws SQLException;

    public abstract long createUser(RegisterForm form) throws SQLException;

    public abstract User getUserByEmail(String email) throws SQLException;

    public abstract User getUserByLogin(String login) throws SQLException;

    public abstract User getUserByLoginAndPassword(LoginForm form) throws SQLException;

    public abstract boolean addUserBalance(AddBalanceForm form, Long userId) throws SQLException;
}
