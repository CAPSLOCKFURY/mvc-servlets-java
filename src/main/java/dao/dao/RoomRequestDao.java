package dao.dao;

import forms.RoomRequestForm;
import models.RoomRequest;
import models.dto.AdminRoomRequestDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public abstract class RoomRequestDao extends AbstractDao {

    public abstract boolean createRoomRequest(RoomRequestForm form) throws SQLException;

    public abstract RoomRequest getRoomRequestById(Long requestId) throws SQLException;

    public abstract List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale) throws SQLException;

    public abstract boolean disableRoomRequest(Long requestId, Long userId) throws SQLException;

    public abstract List<AdminRoomRequestDTO> getRoomRequestsForAdmin(String locale) throws SQLException;

    public abstract AdminRoomRequestDTO getRoomRequestForAdmin(Long requestId, String locale) throws SQLException;

    public abstract boolean confirmRoomRequest(RoomRequest roomRequest, BigDecimal moneyAmount) throws SQLException;

}