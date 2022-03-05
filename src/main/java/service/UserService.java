package service;

import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.RegisterForm;
import models.User;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();

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
            return userDao.createUser(form);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            form.addError("Database error");
            return 0;
        }
    }

    public User getUserById(int id){
        try {
            return userDao.getUserById(id);
        } catch (SQLException sqle){
            throw new DaoException();
        }
    }
}
