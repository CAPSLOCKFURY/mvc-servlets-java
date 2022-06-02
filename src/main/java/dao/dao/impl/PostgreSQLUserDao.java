package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.UserDao;
import db.ConnectionPool;
import exceptions.db.DaoException;
import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLUserDao extends UserDao {

    @Override
    public User getUserById(Long id) {
        try(Connection connection = ConnectionPool.getConnection()){
          return getOneById(connection, SqlQueries.User.FIND_USER_BY_ID, id, User.class);
        } catch (SQLException sqle){
            throw new DaoException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAll(connection, SqlQueries.User.FIND_ALL_USERS, User.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public long createUser(User user) {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntityAndGetId(connection, SqlQueries.User.INSERT_USER, new Object[]{user.getLogin(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getBalance(), user.getRole()});
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_EMAIL, new Object[]{email}, User.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public User getUserByLogin(String login) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_LOGIN, new Object[]{login}, User.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_BY_LOGIN_AND_PASSWORD, new Object[]{login, password}, User.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public boolean updateUser(User user){
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, SqlQueries.User.UPDATE_USER,
                    new Object[]{user.getLogin(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRole(), user.getBalance()},
                    user.getId());
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public User findUserForPasswordChange(String password, Long userId) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.User.FIND_USER_FOR_PASSWORD_CHANGE, new Object[]{password, userId}, User.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

}
