package service;

import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import forms.*;
import models.User;


public class UserService {

    private UserService(){

    }

    private static final class SingletonHolder{
        static final UserService instance = new UserService();
    }

    public static UserService getInstance(){
        return UserService.SingletonHolder.instance;
    }

    public long createUser(RegisterForm form) {
        try(UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao()) {
            if (userDao.getUserByEmail(form.getEmail()).getEmail() != null) {
                form.addLocalizedError("errors.emailExists");
                return 0;
            }
            if (userDao.getUserByLogin(form.getLogin()).getLogin() != null) {
                form.addLocalizedError("errors.loginExists");
                return 0;
            }
            return userDao.createUser(new User(form));
        }
    }

    public User loginUser(LoginForm form){
        try(UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao()) {
            User user = userDao.getUserByLoginAndPassword(form.getLogin(), form.getPassword());
            if (user.getId() == null) {
                form.addLocalizedError("errors.userNotFound");
                return user;
            }
            return user;
        }
    }

    public User getUserById(Long id){
        try(UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao()) {
            return userDao.getUserById(id);
        }
    }

    public boolean addUserBalance(AddBalanceForm form, Long userId){
        try(UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao()) {
            User user = userDao.getUserById(userId);
            user.setBalance(user.getBalance().add(form.getAmount()));
            return userDao.updateUser(user);
        }
    }

    public boolean updateUser(UserUpdateProfileForm form, Long userId){
        try(UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao()) {
            User user = userDao.getUserById(userId);
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            return userDao.updateUser(user);
        }
    }

    public boolean changeUserPassword(ChangePasswordForm form, Long userId){
        try(UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao()) {
            User user = userDao.findUserForPasswordChange(form.getOldPassword(), userId);
            if (user.getId() == null) {
                form.addLocalizedError("errors.IncorrectOldPassword");
                return false;
            }
            user.setPassword(form.getNewPassword());
            return userDao.changePassword(form.getNewPassword(), userId);
        }
    }
}
