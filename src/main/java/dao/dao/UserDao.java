package dao.dao;

import models.User;

import java.util.List;

public abstract class UserDao {
    public abstract User getUserById(int id);

    public abstract List<User> getAllUsers();

    public abstract boolean createUser(User user);
}
