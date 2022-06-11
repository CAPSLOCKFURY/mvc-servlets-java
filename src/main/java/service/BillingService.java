package service;

import dao.dao.BillingDao;
import dao.dao.RoomRequestDao;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Billing;
import models.RoomRequest;
import models.User;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;
import web.base.messages.MessageTransport;

import java.util.List;

public class BillingService {

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
        }
    }

    public boolean payBilling(Long userId, Long billingId, MessageTransport messageTransport){
        BillingDao billingDao = null;
        try {
            billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();
            UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao(billingDao.getConnection());
            RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao(billingDao.getConnection());
            billingDao.transaction.open();

            ExtendedBillingDTO extendedBilling = billingDao.getExtendedBillingById(billingId);
            User user = userDao.getUserById(userId);
            if(extendedBilling.getPaid()){
                messageTransport.addLocalizedMessage("message.billingIsPaid");
            }
            if(user.getBalance().compareTo(extendedBilling.getPrice()) < 0){
                messageTransport.addLocalizedMessage("message.billingNotEnoughMoney");
            }
            if(messageTransport.getMessages().size() != 0){
                return false;
            }

            Billing billing = billingDao.getBillingById(billingId);
            billing.setPaid(true);
            billingDao.updateBilling(billing);
            user.setBalance(user.getBalance().subtract(billing.getPrice()));
            userDao.updateUser(user);
            RoomRequest roomRequest = roomRequestDao.getRoomRequestById(extendedBilling.getRequestId());
            roomRequest.setStatus("paid");
            roomRequestDao.updateRoomRequest(roomRequest);
            billingDao.transaction.commit();
            return true;
        } catch (DaoException sqle){
            billingDao.transaction.rollback();
            return false;
        } finally {
            billingDao.close();
        }
    }

    public int deleteOldBillings(){
        BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();
        try {
            billingDao.transaction.open();
            int result = billingDao.deleteOldBillings();
            billingDao.transaction.commit();
            return result;
        } catch (DaoException sqle){
            sqle.printStackTrace();
            billingDao.transaction.rollback();
            return -1;
        } finally {
            billingDao.close();
        }
    }
}
