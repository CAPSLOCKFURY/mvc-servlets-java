package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.BillingDao;
import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import db.ConnectionPool;
import forms.RoomRequestForm;
import models.RoomRequest;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomRequestDao extends RoomRequestDao {

    private static final BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();

    @Override
    public boolean createRoomRequest(RoomRequestForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntity(connection, SqlQueries.RoomRequest.INSERT_ROOM_REQUEST, form);
        }
    }

    @Override
    public RoomRequest getRoomRequestById(Long requestId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.RoomRequest.FIND_ROOM_REQUEST_BY_ID, new Object[]{"en", requestId}, RoomRequest.class);
        }
    }

    @Override
    public List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale, Pageable pageable) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAllByParams(connection, SqlQueries.RoomRequest.FIND_ROOM_REQUESTS_BY_USER_ID, new Object[]{locale, userId}, RoomRequest.class, pageable);
        }
    }

    @Override
    public boolean disableRoomRequest(Long requestId, Long userId) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(SqlQueries.RoomRequest.DISABLE_REQUEST_BY_ID);
            stmt.setLong(1, requestId);
            stmt.setLong(2, userId);
            return stmt.executeUpdate() == 1;
        }
    }

    @Override
    public List<AdminRoomRequestDTO> getRoomRequestsForAdmin(String locale, String requestStatus, Orderable orderable, Pageable pageable) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return getAllByParams(connection, SqlQueries.RoomRequest.ADMIN_GET_ROOM_REQUESTS, new Object[]{locale, requestStatus}, AdminRoomRequestDTO.class, orderable, pageable);
        }
    }

    @Override
    public AdminRoomRequestDTO getRoomRequestForAdmin(Long requestId, String locale) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.RoomRequest.ADMIN_GET_ROOM_REQUEST_BY_ID, new Object[]{locale, requestId}, AdminRoomRequestDTO.class);
        }
    }

   @Override
   public boolean confirmRoomRequest(RoomRequest roomRequest, BigDecimal moneyAmount) throws SQLException{
        Connection connection = null;
        try{
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            boolean requestConfirmed = updateEntity(connection, SqlQueries.RoomRequest.CONFIRM_ROOM_REQUEST, new Object[]{roomRequest.getId()});
            if(!requestConfirmed){
                connection.rollback();
                return false;
            }
            long roomRegistryInsertedId = createEntityAndGetId(connection, SqlQueries.RoomRequest.INSERT_BOOKED_ROOM_INTO_ROOM_REGISTRY,
                    new Object[]{roomRequest.getUserId(), roomRequest.getRoomId(), roomRequest.getCheckInDate(), roomRequest.getCheckOutDate()});
            if(roomRegistryInsertedId == 0){
                connection.rollback();
                return false;
            }
            boolean billingCreated = billingDao.insertBilling(connection, roomRequest.getId(), moneyAmount, roomRegistryInsertedId);
            if(!billingCreated){
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            if(connection.getAutoCommit() == false) {
                connection.rollback();
            }
            return false;
        } finally {
            if(connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
   }


   public boolean declineAssignedRoom(String comment, Long requestId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, SqlQueries.RoomRequest.DECLINE_ASSIGNED_ROOM, new Object[]{comment}, requestId);
        }
    }

   public boolean adminCloseRequest(Long requestId, String comment) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, SqlQueries.RoomRequest.ADMIN_CLOSE_REQUEST, new Object[]{comment}, requestId);
        }
   }
}
