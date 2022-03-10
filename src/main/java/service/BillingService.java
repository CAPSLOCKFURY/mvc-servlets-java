package service;

import dao.dao.BillingDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Billing;

import java.sql.SQLException;
import java.util.List;

public class BillingService {
    private final static BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();

    public List<Billing> findBillingsByUserId(Long userId){
        try{
            return billingDao.getAllBillingsByUserId(userId);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }
}
