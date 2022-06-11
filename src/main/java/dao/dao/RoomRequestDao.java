package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.RoomRequest;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;

import java.sql.Connection;
import java.util.List;

public abstract class RoomRequestDao extends OrderableAbstractDao {

    public abstract boolean createRoomRequest(RoomRequest roomRequest);

    public abstract boolean updateRoomRequest(RoomRequest roomRequest);

    public abstract RoomRequest getRoomRequestById(Long requestId);

    public abstract List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale, Pageable pageable);

    public abstract List<AdminRoomRequestDTO> getRoomRequestsForAdmin(String locale, String requestStatus, Orderable orderable, Pageable pageable);

    public abstract AdminRoomRequestDTO getRoomRequestForAdmin(Long requestId, String locale);

    public RoomRequestDao(Connection connection) {
        super(connection);
    }
}