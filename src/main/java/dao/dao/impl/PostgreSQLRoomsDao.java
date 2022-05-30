package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.RoomsDao;
import db.ConnectionPool;
import exceptions.db.DaoException;
import models.Room;
import models.RoomClass;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomsDao extends RoomsDao {

    @Override
    public List<Room> getAllRooms(String locale, Orderable orderable, Pageable pageable) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAllByParams(connection, SqlQueries.Room.FIND_ALL_ROOMS, new Object[]{locale}, Room.class, orderable, pageable);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public Room getRoomById(Long id, String locale) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.Room.FIND_ROOM_BY_ID, new Object[]{locale, id}, Room.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public RoomExtendedInfo getExtendedRoomInfoById(Long id, String locale) {
        try(Connection connection = ConnectionPool.getConnection()){
            RoomExtendedInfo room = getOneByParams(connection, SqlQueries.Room.FIND_ROOM_BY_ID, new Object[]{locale, id}, RoomExtendedInfo.class);
            List<RoomDate> roomDates = getAllByParams(connection, SqlQueries.Room.FIND_ROOM_DATES_BY_ID, new Object[]{id}, RoomDate.class);
            room.setDates(roomDates);
            return room;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<RoomClass> getAllRoomClasses(String locale) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAllByParams(connection, SqlQueries.Room.FIND_ALL_ROOM_CLASSES, new Object[]{locale}, RoomClass.class);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public OverlapCountDTO getDatesOverlapCount(java.sql.Date checkInDate, java.sql.Date checkOutDate, Long roomId) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneByParams(connection, SqlQueries.Room.FIND_OVERLAPPING_DATES_COUNT, new Object[]{roomId, checkInDate, checkOutDate}, OverlapCountDTO.class);
        } catch (SQLException sqle){
            throw new DaoException();
        }
    }

    @Override
    public List<RoomHistoryDTO> getRoomHistory(Long userId, String locale, Pageable pageable) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAllByParams(connection, SqlQueries.Room.FIND_ROOM_HISTORY_BY_USER_ID, new Object[]{locale, userId}, RoomHistoryDTO.class, pageable);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Room> findSuitableRoomsForDates(String locale, java.sql.Date checkInDate, java.sql.Date checkOutDate, Orderable orderable, Pageable pageable) {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAllByParams(connection, SqlQueries.Room.FIND_SUITABLE_ROOM_FOR_REQUEST, new Object[]{locale, checkInDate, checkOutDate, checkInDate, checkOutDate}, Room.class, orderable, pageable);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public boolean bookRoom(java.sql.Date checkInDate, java.sql.Date checkOutDate, BigDecimal moneyAmount, Long roomId, Long userId) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            boolean isUpdated = updateEntityById(connection, SqlQueries.Room.WITHDRAW_FROM_USER_BALANCE, new Object[]{moneyAmount}, userId);
            if(!isUpdated){
                connection.rollback();
                return false;
            }
            boolean entityCreated = createEntity(connection, SqlQueries.Room.INSERT_BOOKED_ROOM_INTO_ROOM_REGISTRY, new Object[]{userId, roomId, checkInDate, checkOutDate});
            if(!entityCreated){
                connection.rollback();
                return false;
            }
            updateEntity(connection, SqlQueries.Room.REMOVE_ASSIGNED_ROOM, new Object[]{roomId, checkInDate, checkOutDate});
            connection.commit();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            try {
                if (connection.getAutoCommit() == false) {
                    connection.rollback();
                }
            } catch (SQLException sqle1){
                sqle1.printStackTrace();
                throw new DaoException();
            }
            throw new DaoException();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException sqle){
                sqle.printStackTrace();
            }
        }
    }

    @Override
    public boolean assignRoomToRequest(Long roomId, Long requestId) {
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntityById(connection, SqlQueries.Room.ASSIGN_ROOM_TO_REQUEST, new Object[]{roomId}, requestId);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<RoomRegistryPdfReportDto> findDataForRoomRegistryReport(java.sql.Date checkInDate, java.sql.Date checkOutDate, Pageable pageable) {
        try(Connection connection =  ConnectionPool.getConnection()){
            String sql = SqlQueries.Room.FIND_DATA_FOR_ROOM_REGISTRY_REPORT;
            if(checkInDate != null || checkOutDate != null){
                sql = sql.concat(" where ");
                if(checkInDate != null && checkOutDate != null){
                    sql = sql.concat(" check_in_date >= ? and check_out_date <= ? ");
                    return getAllByParams(connection, sql, new Object[]{checkInDate, checkOutDate}, RoomRegistryPdfReportDto.class, pageable);
                } else if (checkInDate != null){
                    sql = sql.concat(" check_in_date >= ? ");
                    return getAllByParams(connection, sql, new Object[]{checkInDate}, RoomRegistryPdfReportDto.class, pageable);
                } else {
                    sql = sql.concat(" check_out_date <= ? ");
                    return getAllByParams(connection, sql, new Object[]{checkOutDate}, RoomRegistryPdfReportDto.class, pageable);
                }
            }
            return getAll(connection, SqlQueries.Room.FIND_DATA_FOR_ROOM_REGISTRY_REPORT, RoomRegistryPdfReportDto.class, pageable);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public int archiveOldRoomRegistries(){
        try(Connection connection = ConnectionPool.getConnection()){
            return updatePlain(connection, SqlQueries.Room.ARCHIVE_OLD_ROOM_REGISTRIES);
        } catch (SQLException sqle){
            throw new DaoException();
        }
    }

    @Override
    public int updateRoomStatus() {
        try(Connection connection = ConnectionPool.getConnection()){
            return updatePlain(connection, SqlQueries.Room.UPDATE_ROOM_STATUS);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public boolean setRoomUnavailableAndRefundMoney(Long roomId, java.sql.Date endDate) {
        Connection connection = null;
        try{
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            updateEntity(connection, SqlQueries.Room.SET_ROOM_UNAVAILABLE, new Object[]{roomId});
            updateEntity(connection, SqlQueries.Room.REFUND_MONEY_FROM_BILLINGS, new Object[]{endDate, roomId, endDate, roomId});
            updateEntity(connection, SqlQueries.Room.REFUND_MONEY_FROM_ROOM_REGISTRY, new Object[]{endDate, roomId, endDate, roomId});
            updateEntity(connection, SqlQueries.Room.DELETE_REFUNDED_ROOM_REQUESTS, new Object[]{endDate, roomId});
            updateEntity(connection, SqlQueries.Room.DELETE_REFUNDED_BILLINGS, new Object[]{endDate, roomId});
            updateEntity(connection, SqlQueries.Room.DELETE_REFUNDED_ROOM_REGISTRIES, new Object[]{endDate, roomId});
            connection.commit();
            return true;
        } catch (SQLException sqle){
            try {
                sqle.printStackTrace();
                connection.rollback();
            } catch (SQLException sqle1){
                sqle1.printStackTrace();
                throw new DaoException();
            }
            throw new DaoException();
        } finally {
            if(connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean openRoom(Long roomId) {
        try(Connection connection = ConnectionPool.getConnection()){
            return updateEntity(connection, SqlQueries.Room.OPEN_ROOM, new Object[]{roomId});
        } catch (SQLException sqle){
            throw new DaoException();
        }
    }
}
