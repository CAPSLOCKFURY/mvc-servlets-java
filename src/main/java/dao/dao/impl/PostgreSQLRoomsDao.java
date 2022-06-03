package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.RoomsDao;
import exceptions.db.DaoException;
import models.Room;
import models.RoomClass;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomsDao extends RoomsDao {

    @Override
    public boolean updateRoom(Room room) {
        return updateEntityById(SqlQueries.Room.UPDATE_ROOM,
                new Object[]{room.getNumber(), room.getStatus(), room.getName(), room.getPrice(), room.getCapacity(), room.getClassId()}
                , room.getId());
    }

    @Override
    public List<Room> getAllRooms(String locale, Orderable orderable, Pageable pageable) {
        return getAllByParams(SqlQueries.Room.FIND_ALL_ROOMS, new Object[]{locale}, Room.class, orderable, pageable);
    }

    @Override
    public Room getRoomById(Long id, String locale) {
        return getOneByParams(SqlQueries.Room.FIND_ROOM_BY_ID, new Object[]{locale, id}, Room.class);
    }

    @Override
    public RoomExtendedInfo getExtendedRoomInfoById(Long id, String locale) {
        RoomExtendedInfo room = getOneByParams(SqlQueries.Room.FIND_ROOM_BY_ID, new Object[]{locale, id}, RoomExtendedInfo.class);
        List<RoomDate> roomDates = getAllByParams(SqlQueries.Room.FIND_ROOM_DATES_BY_ID, new Object[]{id}, RoomDate.class);
        room.setDates(roomDates);
        return room;
    }

    @Override
    public List<RoomClass> getAllRoomClasses(String locale) {
        return getAllByParams(SqlQueries.Room.FIND_ALL_ROOM_CLASSES, new Object[]{locale}, RoomClass.class);
    }

    @Override
    public OverlapCountDTO getDatesOverlapCount(java.sql.Date checkInDate, java.sql.Date checkOutDate, Long roomId) {
        return getOneByParams(SqlQueries.Room.FIND_OVERLAPPING_DATES_COUNT, new Object[]{roomId, checkInDate, checkOutDate}, OverlapCountDTO.class);
    }

    @Override
    public List<RoomHistoryDTO> getRoomHistory(Long userId, String locale, Pageable pageable) {
        return getAllByParams(SqlQueries.Room.FIND_ROOM_HISTORY_BY_USER_ID, new Object[]{locale, userId}, RoomHistoryDTO.class, pageable);
    }

    @Override
    public List<Room> findSuitableRoomsForDates(String locale, java.sql.Date checkInDate, java.sql.Date checkOutDate, Orderable orderable, Pageable pageable) {
        return getAllByParams(SqlQueries.Room.FIND_SUITABLE_ROOM_FOR_REQUEST, new Object[]{locale, checkInDate, checkOutDate, checkInDate, checkOutDate}, Room.class, orderable, pageable);
    }

    @Override
    public boolean removeAssignedRoomsOnOverlappingDates(Long roomId, Date checkInDate, Date checkOutDate) {
        return updateEntity(SqlQueries.Room.REMOVE_ASSIGNED_ROOM, new Object[]{roomId, checkInDate, checkOutDate});
    }

    @Override
    public List<RoomRegistryPdfReportDto> findDataForRoomRegistryReport(java.sql.Date checkInDate, java.sql.Date checkOutDate, Pageable pageable) {
        String sql = SqlQueries.Room.FIND_DATA_FOR_ROOM_REGISTRY_REPORT;
        if(checkInDate != null || checkOutDate != null){
            sql = sql.concat(" where ");
            if(checkInDate != null && checkOutDate != null){
                sql = sql.concat(" check_in_date >= ? and check_out_date <= ? ");
                return getAllByParams(sql, new Object[]{checkInDate, checkOutDate}, RoomRegistryPdfReportDto.class, pageable);
            } else if (checkInDate != null){
                sql = sql.concat(" check_in_date >= ? ");
                return getAllByParams(sql, new Object[]{checkInDate}, RoomRegistryPdfReportDto.class, pageable);
            } else {
                sql = sql.concat(" check_out_date <= ? ");
                return getAllByParams(sql, new Object[]{checkOutDate}, RoomRegistryPdfReportDto.class, pageable);
            }
        }
        return getAll(SqlQueries.Room.FIND_DATA_FOR_ROOM_REGISTRY_REPORT, RoomRegistryPdfReportDto.class, pageable);
    }

    @Override
    public int archiveOldRoomRegistries(){
        return updatePlain(SqlQueries.Room.ARCHIVE_OLD_ROOM_REGISTRIES);
    }

    @Override
    public int updateRoomStatus() {
        return updatePlain(SqlQueries.Room.UPDATE_ROOM_STATUS);
    }

    @Override
    public boolean setRoomUnavailableAndRefundMoney(Long roomId, java.sql.Date endDate) {
        try{
            connection.setAutoCommit(false);
            updateEntity(SqlQueries.Room.SET_ROOM_UNAVAILABLE, new Object[]{roomId});
            updateEntity(SqlQueries.Room.REFUND_MONEY_FROM_BILLINGS, new Object[]{endDate, roomId, endDate, roomId});
            updateEntity(SqlQueries.Room.REFUND_MONEY_FROM_ROOM_REGISTRY, new Object[]{endDate, roomId, endDate, roomId});
            updateEntity(SqlQueries.Room.DELETE_REFUNDED_ROOM_REQUESTS, new Object[]{endDate, roomId});
            updateEntity(SqlQueries.Room.DELETE_REFUNDED_BILLINGS, new Object[]{endDate, roomId});
            updateEntity(SqlQueries.Room.DELETE_REFUNDED_ROOM_REGISTRIES, new Object[]{endDate, roomId});
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
                    //connection.close();
                } catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }
    }


    public PostgreSQLRoomsDao(Connection connection) {
        super(connection);
    }
}
