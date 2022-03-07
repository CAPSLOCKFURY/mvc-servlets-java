package dao.dao;

import forms.RoomRequestForm;
import models.RoomRequest;

import java.sql.SQLException;
import java.util.List;

public abstract class RoomRequestDao extends AbstractDao {

    public abstract boolean createRoomRequest(RoomRequestForm form) throws SQLException;

    public abstract List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale) throws SQLException;

    public abstract boolean disableRoomRequest(Long requestId, Long userId) throws SQLException;

}