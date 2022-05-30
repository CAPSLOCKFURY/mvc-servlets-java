package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.RoomRequest;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;

import java.math.BigDecimal;
import java.util.List;

public abstract class RoomRequestDao extends OrderableAbstractDao {

    public abstract boolean createRoomRequest(RoomRequest roomRequest);

    public abstract RoomRequest getRoomRequestById(Long requestId);

    public abstract List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale, Pageable pageable);

    public abstract boolean disableRoomRequest(Long requestId, Long userId);

    public abstract List<AdminRoomRequestDTO> getRoomRequestsForAdmin(String locale, String requestStatus, Orderable orderable, Pageable pageable);

    public abstract AdminRoomRequestDTO getRoomRequestForAdmin(Long requestId, String locale);

    public abstract boolean confirmRoomRequest(RoomRequest roomRequest, BigDecimal moneyAmount);

    public abstract boolean declineAssignedRoom(String comment, Long requestId);

    /**
     * Closes room request and inserts manager comment into it
     */
    public abstract boolean adminCloseRequest(Long requestId, String comment);
}