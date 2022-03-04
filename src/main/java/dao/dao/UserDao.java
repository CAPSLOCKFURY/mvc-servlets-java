package dao.dao;

import forms.RegisterForm;
import models.User;

import java.util.List;

public abstract class UserDao {
    public abstract User getUserById(int id);

    public abstract List<User> getAllUsers();

    public abstract boolean createUser(RegisterForm form);
}
