package service;

import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.*;
import models.User;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();

    private UserService(){

    }

    private static final class SingletonHolder{
        static final UserService instance = new UserService();
    }

    public static UserService getInstance(){
        return UserService.SingletonHolder.instance;
    }

    public long createUser(RegisterForm form) {
        try {
            if(userDao.getUserByEmail(form.getEmail()).getEmail() != null){
                form.addLocalizedError("errors.emailExists");
                return 0;
            }
            if(userDao.getUserByLogin(form.getLogin()).getLogin() != null){
                form.addLocalizedError("errors.loginExists");
                return 0;
            }
            return userDao.createUser(new User(form), form.getPassword());
        } catch (DaoException sqle){
            form.addError("Database error");
            return 0;
        }
    }

    public User loginUser(LoginForm form){
        try{
            User user = userDao.getUserByLoginAndPassword(form.getLogin(), form.getPassword());
            if(user.getId() == null){
                form.addLocalizedError("errors.userNotFound");
                return user;
            }
            return user;
        } catch (DaoException e){
            form.addError("Database error");
            return new User();
        }
    }

    public User getUserById(Long id){
        return userDao.getUserById(id);
    }

    public boolean addUserBalance(AddBalanceForm form, Long userId){
        try {
            return userDao.addUserBalance(form.getAmount(), userId);
        } catch (DaoException daoException){
            form.addError("Database error");
            return false;
        }
    }

    public boolean updateUser(UserUpdateProfileForm form, Long userId){
        try{
            return userDao.updateUser(form.getFirstName(), form.getLastName(), userId);
        } catch (DaoException daoException){
            form.addError("Database error");
            return false;
        }
    }

    public boolean changeUserPassword(ChangePasswordForm form, Long userId){
        try {
            User user = userDao.findUserForPasswordChange(form.getOldPassword(), userId);
            if(user.getId() == null){
                form.addLocalizedError("errors.IncorrectOldPassword");
                return false;
            }
            return userDao.changePassword(form.getNewPassword(), userId);
        } catch (DaoException daoException){
            form.addError("Database Error");
            return false;
        }
    }
}
