package service;

import dao.dao.BillingDao;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Billing;
import models.User;
import models.dto.ExtendedBillingDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class BillingService {
    private final static BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();
    private final static UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();

    public List<Billing> findBillingsByUserId(Long userId){
        try{
            return billingDao.getAllBillingsByUserId(userId);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean payBilling(Long userId, Long billingId){
        try{
            //TODO add error showing for this
            ExtendedBillingDTO billing = billingDao.getBillingById(billingId);
            User user = userDao.getUserById(userId);
            if(billing.getPaid()){
                return false;
            }
            if(!billing.getUserId().equals(userId)){
                return false;
            }
            if(user.getBalance().compareTo(billing.getPrice()) < 0){
                return false;
            }
            return billingDao.payBilling(userId, billing);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
