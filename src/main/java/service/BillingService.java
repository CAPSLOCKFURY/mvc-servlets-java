package service;

import dao.dao.BillingDao;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Billing;
import models.User;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;
import web.base.messages.MessageTransport;

import java.sql.SQLException;
import java.util.List;

public class BillingService {

    //private static final BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();
    //private static final UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();

    private BillingService(){

    }

    private static final class SingletonHolder{
        static final BillingService instance = new BillingService();
    }

    public static BillingService getInstance(){
        return BillingService.SingletonHolder.instance;
    }

    public List<Billing> findBillingsByUserId(Long userId, Pageable pageable){
        try(BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();){
            return billingDao.getAllBillingsByUserId(userId, pageable);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean payBilling(Long userId, Long billingId, MessageTransport messageTransport){
        try(BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();
            UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao(billingDao.getConnection());)
        {
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

    public int deleteOldBillings(){
        try(BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();){
            return billingDao.deleteOldBillings();
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
    }
}
