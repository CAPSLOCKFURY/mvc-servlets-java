package dao.dao.impl;

import dao.dao.BillingDao;
import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import db.ConnectionPool;
import forms.RoomRequestForm;
import models.RoomRequest;
import models.base.SqlColumn;
import models.base.SqlType;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomRequestDao extends RoomRequestDao {

    private final static BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao();

    private final static String INSERT_ROOM_REQUEST = "insert into room_requests (user_id, capacity, room_class, check_in_date, check_out_date, comment)\n" +
            "values (?, ?, ?, ?, ?, ?)";

    private final static String FIND_ROOM_REQUESTS_BY_USER_ID = "select room_requests.*, rct.name as class_name from room_requests\n" +
            "    left outer join room_class_translation rct on rct.class_id = room_requests.room_class and rct.language = ?\n" +
            "    where user_id = ? order by -room_requests.id";

    private final static String DISABLE_REQUEST_BY_ID = "update room_requests set status = 'closed' where id = ? and user_id = ?";

    private final static String ADMIN_GET_ROOM_REQUESTS = "select room_requests.id, capacity, rct.name as class_name, comment, manager_comment, status, check_in_date, check_out_date, login, email, first_name, last_name from room_requests\n" +
            "    left outer join room_class_translation rct on room_requests.room_class = rct.class_id and rct.language = ?\n" +
            "    left outer join users u on room_requests.user_id = u.id\n" +
            "where room_requests.status = 'awaiting'\n" +
            "order by room_requests.id";

    private final static String ADMIN_GET_ROOM_REQUEST_BY_ID = "select room_requests.id, capacity, rct.name as class_name, comment, manager_comment, status, check_in_date, check_out_date, login, email, first_name, last_name from room_requests\n" +
            "    left outer join room_class_translation rct on room_requests.room_class = rct.class_id and rct.language = ?\n" +
            "    left outer join users u on room_requests.user_id = u.id\n" +
            "where room_requests.id = ?\n" +
            "order by room_requests.id";

    private final static String CONFIRM_ROOM_REQUEST = "update room_requests set status = 'awaiting payment' where id=?";

    private final static String FIND_ROOM_REQUEST_BY_ID = "select room_requests.*, rct.name as class_name from room_requests\n" +
            "    left outer join room_class_translation rct on rct.class_id = room_requests.room_class and rct.language = ?\n" +
            "    where room_requests.id = ?";

    private final static String INSERT_BOOKED_ROOM_INTO_ROOM_REGISTRY = "insert into room_registry(user_id, room_id, check_in_date, check_out_date) values (?, ?, ?, ?)";

    private final static String DECLINE_ASSIGNED_ROOM = "update room_requests set room_id = null, status = 'awaiting', comment=? where id = ?";

    private final static String ADMIN_CLOSE_REQUEST = "update room_requests set manager_comment = ?, status = 'closed' where id=?";

    @Override
    public boolean createRoomRequest(RoomRequestForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntity(connection, INSERT_ROOM_REQUEST, form);
        }
    }

    @Override
    public RoomRequest getRoomRequestById(Long requestId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Params{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = "en";
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = requestId;
                public String getLang() {return lang;}
                public Long getId() {return id;}
            }
            return getOneByParams(connection, FIND_ROOM_REQUEST_BY_ID, new Params(), RoomRequest.class);
        }
    }

    @Override
    public List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale, Pageable pageable) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = userId;
                private String getLang(){return lang;}
                public Long getId() {return id;}
            }
            return getAllByParams(connection, FIND_ROOM_REQUESTS_BY_USER_ID, new Param(), RoomRequest.class, pageable);
        }
    }

    @Override
    public boolean disableRoomRequest(Long requestId, Long userId) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(DISABLE_REQUEST_BY_ID);
            stmt.setLong(1, requestId);
            stmt.setLong(2, userId);
            return stmt.executeUpdate() == 1;
        }
    }

    @Override
    public List<AdminRoomRequestDTO> getRoomRequestsForAdmin(String locale) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                public String getLang() {return lang;}
            }
            return getAllByParams(connection, ADMIN_GET_ROOM_REQUESTS, new Param(), AdminRoomRequestDTO.class);
        }
    }

    @Override
    public AdminRoomRequestDTO getRoomRequestForAdmin(Long requestId, String locale) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Params{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = requestId;
                public String getLang() {return lang;}
                public Long getId() {return id;}
            }
            return getOneByParams(connection, ADMIN_GET_ROOM_REQUEST_BY_ID, new Params(), AdminRoomRequestDTO.class);
        }
    }

   @Override
   public boolean confirmRoomRequest(RoomRequest roomRequest, BigDecimal moneyAmount) throws SQLException{
        Connection connection = null;
        try{
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            class UpdateParam{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = roomRequest.getId();
                public Long getId() {return id;}
            }
            boolean requestConfirmed = updateEntity(connection, CONFIRM_ROOM_REQUEST, new UpdateParam());
            if(!requestConfirmed){
                connection.rollback();
                return false;
            }
            class RoomRegistryInsert{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long roomRegistryUserId = roomRequest.getUserId();
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long roomRegistryRoomId = roomRequest.getRoomId();
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date checkInDate = roomRequest.getCheckInDate();
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date checkOutDate = roomRequest.getCheckOutDate();
                public Long getRoomRegistryUserId() {return roomRegistryUserId;}
                public Long getRoomRegistryRoomId() {return roomRegistryRoomId;}
                public Date getCheckInDate() {return checkInDate;}
                public Date getCheckOutDate() {return checkOutDate;}
            }
            long roomRegistryInsertedId = createEntityAndGetId(connection, INSERT_BOOKED_ROOM_INTO_ROOM_REGISTRY, new RoomRegistryInsert());
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
            class UpdateParams{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String newComment = comment;
                public String getNewComment() {return newComment;}
            }
            return updateEntityById(connection, DECLINE_ASSIGNED_ROOM, new UpdateParams(), requestId);
        }
    }

   public boolean adminCloseRequest(Long requestId, String comment) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class UpdateParam{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String managerComment = comment;
                public String getManagerComment() {return managerComment;}
            }
            return updateEntityById(connection, ADMIN_CLOSE_REQUEST, new UpdateParam(), requestId);
        }
   }
}
