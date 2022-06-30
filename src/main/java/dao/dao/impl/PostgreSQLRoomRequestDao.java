package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.RoomRequestDao;
import models.RoomRequest;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.dto.IsRoomAssigned;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class PostgreSQLRoomRequestDao extends RoomRequestDao {

    @Override
    public boolean createRoomRequest(RoomRequest roomRequest) {
        return createEntity(SqlQueries.RoomRequest.INSERT_ROOM_REQUEST,
                new Object[]{roomRequest.getUserId(), roomRequest.getCapacity(), roomRequest.getRoomClassId(), roomRequest.getCheckInDate(),
                        roomRequest.getCheckOutDate(), roomRequest.getComment(), roomRequest.getStatus(), roomRequest.getRoomId(), roomRequest.getManagerComment()});
    }

    @Override
    public boolean updateRoomRequest(RoomRequest roomRequest) {
        return updateEntityById(SqlQueries.RoomRequest.UPDATE_ROOM_REQUEST,
                new Object[]{roomRequest.getUserId(), roomRequest.getCapacity(), roomRequest.getRoomClassId(), roomRequest.getCheckInDate(), roomRequest.getCheckOutDate()
                ,roomRequest.getComment(), roomRequest.getStatus(), roomRequest.getRoomId(), roomRequest.getManagerComment()},
                roomRequest.getId());
    }

    @Override
    public RoomRequest getRoomRequestById(Long requestId) {
        return getOneByParams(SqlQueries.RoomRequest.FIND_ROOM_REQUEST_BY_ID, new Object[]{"en", requestId}, RoomRequest.class);
    }

    @Override
    public List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale, Pageable pageable) {
        return getAllByParams(SqlQueries.RoomRequest.FIND_ROOM_REQUESTS_BY_USER_ID, new Object[]{locale, userId}, RoomRequest.class, pageable);
    }

    @Override
    public List<AdminRoomRequestDTO> getRoomRequestsForAdmin(String locale, String requestStatus, Orderable orderable, Pageable pageable){
        return getAllByParams(SqlQueries.RoomRequest.ADMIN_GET_ROOM_REQUESTS, new Object[]{locale, requestStatus}, AdminRoomRequestDTO.class, orderable, pageable);
    }

    @Override
    public AdminRoomRequestDTO getRoomRequestForAdmin(Long requestId, String locale){
        return getOneByParams(SqlQueries.RoomRequest.ADMIN_GET_ROOM_REQUEST_BY_ID, new Object[]{locale, requestId}, AdminRoomRequestDTO.class);
    }

    @Override
    public IsRoomAssigned isRoomAssigned(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return getOneByParams(SqlQueries.Room.IS_ROOM_ASSIGNED_TO_ROOM_REQUEST, new Object[]{roomId, checkInDate, checkOutDate}, IsRoomAssigned.class);
    }


    public PostgreSQLRoomRequestDao(Connection connection) {
        super(connection);
    }
}
