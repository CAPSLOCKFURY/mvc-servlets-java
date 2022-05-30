package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.User;

import java.math.BigDecimal;
import java.util.List;

public abstract class UserDao extends OrderableAbstractDao {

    public abstract User getUserById(Long id);

    public abstract List<User> getAllUsers();

    public abstract long createUser(User user, String password);

    public abstract User getUserByEmail(String email);

    public abstract User getUserByLogin(String login);

    public abstract User getUserByLoginAndPassword(String login, String password);

    public abstract boolean addUserBalance(BigDecimal amount, Long userId);

    public abstract boolean updateUser(String firstName, String lastName, Long userId);

    public abstract boolean changePassword(String newPassword, Long userId);

    public abstract User findUserForPasswordChange(String password, Long userId);

}