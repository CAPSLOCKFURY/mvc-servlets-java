package dao.dao;

import models.Room;
import models.dto.RoomExtendedInfo;

import java.sql.SQLException;
import java.util.List;

public abstract class RoomsDao extends AbstractDao {

    public abstract List<RoomExtendedInfo> getAllRooms() throws SQLException;

    public abstract Room getRoomById(Long id) throws SQLException;

}
