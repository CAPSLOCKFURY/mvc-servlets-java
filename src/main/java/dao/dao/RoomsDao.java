package dao.dao;

import forms.BookRoomForm;
import models.Room;
import models.RoomClass;
import models.dto.OverlapCountDTO;
import models.dto.RoomExtendedInfo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public abstract class RoomsDao extends AbstractDao {

    public abstract List<Room> getAllRooms(String locale) throws SQLException;

    public abstract Room getRoomById(Long id, String locale) throws SQLException;

    public abstract List<RoomClass> getAllRoomClasses(String locale) throws SQLException;

    public abstract RoomExtendedInfo getExtendedRoomInfoById(Long id, String locale) throws SQLException;

    public abstract OverlapCountDTO getDatesOverlapCount(java.sql.Date checkInDate, java.sql.Date checkOutDate, Long roomId) throws SQLException;

    public abstract boolean bookRoom(BookRoomForm form, BigDecimal moneyAmount, Long roomId, Long userId) throws SQLException;
}
