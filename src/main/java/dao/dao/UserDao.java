package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.User;

import java.sql.Connection;
import java.util.List;

public abstract class UserDao extends OrderableAbstractDao {

    public abstract User getUserById(Long id);

    public abstract long createUser(User user);

    public abstract User getUserByEmail(String email);

    public abstract User getUserByLogin(String login);

    public abstract User getUserByLoginAndPassword(String login, String password);

    public abstract boolean updateUser(User user);

    public abstract User findUserForPasswordChange(String password, Long userId);

    public abstract List<User> getAllUsers();

    public abstract boolean changePassword(String newPassword, Long userId);

    public UserDao(Connection connection) {
        super(connection);
    }

}