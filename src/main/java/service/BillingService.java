package service;

import commands.base.messages.MessageTransport;
import dao.dao.BillingDao;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Billing;
import models.User;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;

import java.sql.SQLException;
import java.util.List;

public class BillingService {

    private final static BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();
    private final static UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();

    private BillingService(){

    }

    private static final class SingletonHolder{
        static final BillingService instance = new BillingService();
    }

    public static BillingService getInstance(){
        return BillingService.SingletonHolder.instance;
    }

    public List<Billing> findBillingsByUserId(Long userId, Pageable pageable){
        try{
            return billingDao.getAllBillingsByUserId(userId, pageable);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean payBilling(Long userId, Long billingId, MessageTransport messageTransport){
        try{
            ExtendedBillingDTO billing = billingDao.getBillingById(billingId);
            User user = userDao.getUserById(userId);
            if(billing.getPaid()){
                messageTransport.addLocalizedMessage("message.billingIsPaid");
            }
            if(!billing.getUserId().equals(userId)){
                messageTransport.addLocalizedMessage("message.thisIsNotYourBilling");
            }
            if(user.getBalance().compareTo(billing.getPrice()) < 0){
                messageTransport.addLocalizedMessage("message.billingNotEnoughMoney");
            }
            if(messageTransport.getMessages().size() != 0){
                return false;
            }
            return billingDao.payBilling(userId, billing);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
