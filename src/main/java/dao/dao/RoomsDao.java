package dao.dao;

import models.Room;

import java.sql.SQLException;
import java.util.List;

public abstract class RoomsDao extends AbstractDao {

    public abstract List<Room> getAllRooms() throws SQLException;

}
