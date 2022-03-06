package dao.dao;

import forms.RoomRequestForm;

import java.sql.SQLException;

public abstract class RoomRequestDao extends AbstractDao {

    public abstract boolean createRoomRequest(RoomRequestForm form) throws SQLException;

}