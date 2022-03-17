package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.RoomsDao;
import db.ConnectionPool;
import forms.BookRoomForm;
import models.Room;
import models.RoomClass;
import models.base.SqlColumn;
import models.base.SqlType;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomsDao extends RoomsDao {

    @Override
    public List<Room> getAllRooms(String locale, Orderable orderable, Pageable pageable) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                public String getLang() {return lang;}
            }
            return getAllByParams(connection, SqlQueries.Room.FIND_ALL_ROOMS, new Param(), Room.class, orderable, pageable);
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
            return getOneByParams(connection, SqlQueries.Room.FIND_ROOM_BY_ID, new Param(), Room.class);
        }
    }

    @Override
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
            RoomExtendedInfo room = getOneByParams(connection, SqlQueries.Room.FIND_ROOM_BY_ID, new Param(), RoomExtendedInfo.class);
            List<RoomDate> roomDates = getAllByParams(connection, SqlQueries.Room.FIND_ROOM_DATES_BY_ID, new DatesParam(), RoomDate.class);
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
            return getAllByParams(connection, SqlQueries.Room.FIND_ALL_ROOM_CLASSES, new Param(), RoomClass.class);
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
            return getOneByParams(connection, SqlQueries.Room.FIND_OVERLAPPING_DATES_COUNT, new OverlapParams(), OverlapCountDTO.class);
        }
    }

    @Override
    public List<RoomHistoryDTO> getRoomHistory(Long userId, String locale, Pageable pageable) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Params{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private Long id = userId;
                public String getLang() {return lang;}
                public Long getId() {return id;}
            }
            return getAllByParams(connection, SqlQueries.Room.FIND_ROOM_HISTORY_BY_USER_ID, new Params(), RoomHistoryDTO.class, pageable);
        }
    }

    @Override
    public List<Room> findSuitableRoomsForDates(String locale, java.sql.Date checkInDate, java.sql.Date checkOutDate, Orderable orderable, Pageable pageable) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Params{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date startDate = checkInDate;
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date endDate = checkOutDate;
                public String getLang() {return lang;}
                public Date getStartDate() {return startDate;}
                public Date getEndDate() {return endDate;}
            }
            return getAllByParams(connection, SqlQueries.Room.FIND_SUITABLE_ROOM_FOR_REQUEST, new Params(), Room.class, orderable, pageable);
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
            boolean isUpdated = updateEntityById(connection, SqlQueries.Room.WITHDRAW_FROM_USER_BALANCE, new WithdrawAmount(), userId);
            if(!isUpdated){
                connection.rollback();
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
            boolean entityCreated = createEntity(connection, SqlQueries.Room.INSERT_BOOKED_ROOM_INTO_ROOM_REGISTRY, new RoomRegistryInsert());
            if(!entityCreated){
                connection.rollback();
                return false;
            }
            class RemoveAssignedRoomParams{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = roomId;
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date checkInDate = form.getCheckInDate();
                @SqlColumn(columnName = "", type = SqlType.DATE)
                private final java.sql.Date checkOutDate = form.getCheckOutDate();
                public Long getId() {return id;}
                public Date getCheckInDate() {return checkInDate;}
                public Date getCheckOutDate() {return checkOutDate;}
            }
            updateEntity(connection, SqlQueries.Room.REMOVE_ASSIGNED_ROOM, new RemoveAssignedRoomParams());
            connection.commit();
            return true;
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

    @Override
    public boolean assignRoomToRequest(Long roomId, Long requestId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private Long room = roomId;
                public Long getRoom() {return room;}
            }
            return updateEntityById(connection, SqlQueries.Room.ASSIGN_ROOM_TO_REQUEST, new Param(), requestId);
        }
    }

    @Override
    public List<RoomRegistryPdfReportDto> findDataForRoomRegistryReport(java.sql.Date checkInDate, java.sql.Date checkOutDate, Pageable pageable) throws SQLException{
        try(Connection connection =  ConnectionPool.getConnection()){
            String sql = SqlQueries.Room.FIND_DATA_FOR_ROOM_REGISTRY_REPORT;
            if(checkInDate != null || checkOutDate != null){
                sql = sql.concat(" where ");
                if(checkInDate != null && checkOutDate != null){
                    class TwoDatesParam{
                        @SqlColumn(columnName = "", type = SqlType.DATE)
                        private final java.sql.Date checkIn = checkInDate;
                        @SqlColumn(columnName = "", type = SqlType.DATE)
                        private final java.sql.Date checkOut = checkOutDate;
                        public Date getCheckIn() {return checkIn;}
                        public Date getCheckOut() {return checkOut;}
                    }
                    sql = sql.concat(" check_in_date >= ? and check_out_date <= ? ");
                    return getAllByParams(connection, sql, new TwoDatesParam(), RoomRegistryPdfReportDto.class, pageable);
                } else if (checkInDate != null){
                    class SingleInDateParam{
                        @SqlColumn(columnName = "", type = SqlType.DATE)
                        private final java.sql.Date checkIn = checkInDate;
                        public Date getCheckIn() {return checkIn;}
                    }
                    sql = sql.concat(" check_in_date >= ? ");
                    return getAllByParams(connection, sql, new SingleInDateParam(), RoomRegistryPdfReportDto.class, pageable);
                } else {
                    class SingleOutDateParam{
                        @SqlColumn(columnName = "", type = SqlType.DATE)
                        private final java.sql.Date checkOut = checkOutDate;
                        public Date getCheckOut() {return checkOut;}
                    }
                    sql = sql.concat(" check_out_date <= ? ");
                    return getAllByParams(connection, sql, new SingleOutDateParam(), RoomRegistryPdfReportDto.class, pageable);
                }
            }
            return getAll(connection, SqlQueries.Room.FIND_DATA_FOR_ROOM_REGISTRY_REPORT, RoomRegistryPdfReportDto.class, pageable);
        }
    }
}
