package dao.dao;

import forms.BookRoomForm;
import models.Room;
import models.RoomClass;
import models.base.pagination.Pageable;
import models.dto.OverlapCountDTO;
import models.dto.RoomExtendedInfo;
import models.dto.RoomHistoryDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public abstract class RoomsDao extends PageableAbstractDao {

    public abstract List<Room> getAllRooms(String locale, Pageable pageable) throws SQLException;

    public abstract Room getRoomById(Long id, String locale) throws SQLException;

    public abstract List<RoomClass> getAllRoomClasses(String locale) throws SQLException;

    public abstract RoomExtendedInfo getExtendedRoomInfoById(Long id, String locale) throws SQLException;

    public abstract OverlapCountDTO getDatesOverlapCount(java.sql.Date checkInDate, java.sql.Date checkOutDate, Long roomId) throws SQLException;

    public abstract List<RoomHistoryDTO> getRoomHistory(Long userId, String locale) throws SQLException;

    public abstract boolean bookRoom(BookRoomForm form, BigDecimal moneyAmount, Long roomId, Long userId) throws SQLException;

    public abstract List<Room> findSuitableRoomsForDates(String locale, java.sql.Date checkInDate, java.sql.Date checkOutDate) throws SQLException;

    public abstract boolean assignRoomToRequest(Long roomId, Long requestId) throws SQLException;
}
