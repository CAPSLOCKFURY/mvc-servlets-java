package dao.dao;

import forms.RoomRequestForm;
import models.RoomRequest;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public abstract class RoomRequestDao extends OrderableAbstractDao {

    public abstract boolean createRoomRequest(RoomRequestForm form) throws SQLException;

    public abstract RoomRequest getRoomRequestById(Long requestId) throws SQLException;

    public abstract List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale, Pageable pageable) throws SQLException;

    public abstract boolean disableRoomRequest(Long requestId, Long userId) throws SQLException;

    public abstract List<AdminRoomRequestDTO> getRoomRequestsForAdmin(String locale, Pageable pageable) throws SQLException;

    public abstract AdminRoomRequestDTO getRoomRequestForAdmin(Long requestId, String locale) throws SQLException;

    public abstract boolean confirmRoomRequest(RoomRequest roomRequest, BigDecimal moneyAmount) throws SQLException;

    public abstract boolean declineAssignedRoom(String comment, Long requestId) throws SQLException;

    public abstract boolean adminCloseRequest(Long requestId, String comment) throws SQLException;
}