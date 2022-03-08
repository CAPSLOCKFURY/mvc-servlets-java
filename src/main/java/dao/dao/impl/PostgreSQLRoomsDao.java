package dao.dao.impl;

import dao.dao.RoomsDao;
import db.ConnectionPool;
import forms.BookRoomForm;
import models.Room;
import models.RoomClass;
import models.base.SqlColumn;
import models.base.SqlType;
import models.dto.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class PostgreSQLRoomsDao extends RoomsDao {

    //TODO add dynamic ordering
    private final static String FIND_ALL_ROOMS = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status,\n" +
            "       rooms.price as price, rooms.capacity as capacity, rct.name as class_name from rooms" +
            "    left outer join room_class rc on rooms.class = rc.id\n" +
            "    left outer join room_class_translation rct on rc.id = rct.class_id and language = ?\n" +
            "order by rc.id";

    private final static String FIND_ROOM_BY_ID = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status,\n" +
            "       rooms.price as price, rooms.capacity as capacity, rct.name as class_name from rooms\n" +
            "    left outer join room_class_translation rct on rooms.class = rct.class_id and language = ?\n" +
            "where rooms.id = ?";

    private final static String FIND_ALL_ROOM_CLASSES = "select class_id as id, name from room_class_translation where language = ?";

    private final static String FIND_ROOM_DATES_BY_ID = "select check_in_date, check_out_date from room_registry where room_id = ? and archived = false";

    private final static String FIND_OVERLAPPING_DATES_COUNT = "select count('*') as cnt from room_registry\n" +
            "where room_id = ? and (daterange(?::date, ?::date, '[]') &&\n" +
            "      daterange(room_registry.check_in_date::date, room_registry.check_out_date::date, '[]') )";

    private final static String GET_USER_BALANCE = "select balance from users where id = ?";

    private final static String WITHDRAW_FROM_USER_BALANCE = "update users set balance = balance - ? where id = ?";

    private final static String INSERT_BOOKED_ROOM_INTO_ROOM_REGISTRY = "insert into room_registry(user_id, room_id, check_in_date, check_out_date) values (?, ?, ?, ?)";

    @Override
    public List<Room> getAllRooms(String locale) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                public String getLang() {return lang;}
            }
            return getAllByParams(connection, FIND_ALL_ROOMS, new Param(), Room.class);
        }
    }

    @Override
    public Room getRoomById(Long id, String locale) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long entityId = id;
                public String getLang() {return lang;}
                public Long getEntityId() {return entityId;}
            }
            return getOneByParams(connection, FIND_ROOM_BY_ID, new Param(), Room.class);
        }
    }

    public RoomExtendedInfo getExtendedRoomInfoById(Long id, String locale) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long entityId = id;
                public String getLang() {return lang;}
                public Long getEntityId() {return entityId;}
            }
            class DatesParam{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long roomId = id;
                public Long getRoomId() {return roomId;}
            }
            RoomExtendedInfo room = getOneByParams(connection, FIND_ROOM_BY_ID, new Param(), RoomExtendedInfo.class);
            List<RoomDate> roomDates = getAllByParams(connection, FIND_ROOM_DATES_BY_ID, new DatesParam(), RoomDate.class);
            room.setDates(roomDates);
            return room;
        }
    }

    @Override
    public List<RoomClass> getAllRoomClasses(String locale) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                public String getLang() {return lang;}
            }
            return getAllByParams(connection, FIND_ALL_ROOM_CLASSES, new Param(), RoomClass.class);
        }
    }

    @Override
    public OverlapCountDTO getDatesOverlapCount(java.sql.Date checkInDate, java.sql.Date checkOutDate, Long roomId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class OverlapParams{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = roomId;
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date checkInDateParam = checkInDate;
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date checkOutDateParam = checkOutDate;
                public Date getCheckInDateParam() {return checkInDateParam;}
                public Date getCheckOutDateParam() {return checkOutDateParam;}
                public Long getId() {return id;}
            }
            return getOneByParams(connection, FIND_OVERLAPPING_DATES_COUNT, new OverlapParams(), OverlapCountDTO.class);
        }
    }

    @Override
    public boolean bookRoom(BookRoomForm form, BigDecimal moneyAmount, Long roomId, Long userId) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            class WithdrawAmount{
                @SqlColumn(columnName = "", type = SqlType.DECIMAL)
                private BigDecimal amount = moneyAmount;
                public BigDecimal getAmount() {return amount;}
                public void setAmount(BigDecimal amount) {this.amount = amount;}
            }
            boolean isUpdated = updateEntityById(connection, WITHDRAW_FROM_USER_BALANCE, new WithdrawAmount(), userId);
            if(!isUpdated){
                return false;
            }
            class RoomRegistryInsert {
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long user_id = userId;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long room_id = roomId;
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final Date checkInDate = form.getCheckInDate();
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final Date checkOutDate = form.getCheckOutDate();
                public Long getUser_id() {return user_id;}
                public Long getRoom_id() {return room_id;}
                public Date getCheckInDate() {return checkInDate;}
                public Date getCheckOutDate() {return checkOutDate;}
            }
            boolean entityCreated = createEntity(connection, INSERT_BOOKED_ROOM_INTO_ROOM_REGISTRY, new RoomRegistryInsert());
            if(entityCreated){
                connection.commit();
                return true;
            }
            return false;
        } catch (SQLException sqle) {
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
}
