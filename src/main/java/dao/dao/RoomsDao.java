package dao.dao;

import models.Room;
import models.RoomClass;
import models.dto.RoomExtendedInfo;

import java.sql.SQLException;
import java.util.List;

public abstract class RoomsDao extends AbstractDao {

    public abstract List<RoomExtendedInfo> getAllRooms(String locale) throws SQLException;

    public abstract RoomExtendedInfo getRoomById(Long id, String locale) throws SQLException;

    public abstract List<RoomClass> getAllRoomClasses(String locale) throws SQLException;
}
