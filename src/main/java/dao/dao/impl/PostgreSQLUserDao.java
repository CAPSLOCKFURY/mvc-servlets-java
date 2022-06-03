package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.UserDao;
import models.User;

import java.sql.Connection;
import java.util.List;

public class PostgreSQLUserDao extends UserDao {

    @Override
    public User getUserById(Long id) {
        return getOneById(SqlQueries.User.FIND_USER_BY_ID, id, User.class);
    }

    @Override
    public List<User> getAllUsers() {
        return getAll(SqlQueries.User.FIND_ALL_USERS, User.class);
    }

    @Override
    public long createUser(User user) {
        return createEntityAndGetId(SqlQueries.User.INSERT_USER, new Object[]{user.getLogin(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getBalance(), user.getRole()});
    }

    @Override
    public User getUserByEmail(String email) {
        return getOneByParams(SqlQueries.User.FIND_BY_EMAIL, new Object[]{email}, User.class);
    }

    @Override
    public User getUserByLogin(String login) {
        return getOneByParams(SqlQueries.User.FIND_BY_LOGIN, new Object[]{login}, User.class);
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        return getOneByParams(SqlQueries.User.FIND_BY_LOGIN_AND_PASSWORD, new Object[]{login, password}, User.class);
    }

    @Override
    public boolean updateUser(User user){
            return updateEntityById(SqlQueries.User.UPDATE_USER,
                    new Object[]{user.getLogin(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRole(), user.getBalance()},
                    user.getId());
    }

    @Override
    public User findUserForPasswordChange(String password, Long userId) {
        return getOneByParams(SqlQueries.User.FIND_USER_FOR_PASSWORD_CHANGE, new Object[]{password, userId}, User.class);
    }

    public PostgreSQLUserDao(Connection connection) {
        super(connection);
    }


}
