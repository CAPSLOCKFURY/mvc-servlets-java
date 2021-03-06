package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.Room;
import models.RoomClass;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.OverlapCountDTO;
import models.dto.RoomExtendedInfo;
import models.dto.RoomHistoryDTO;
import models.dto.RoomRegistryPdfReportDto;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public abstract class RoomsDao extends OrderableAbstractDao {

    public abstract boolean updateRoom(Room room);

    public abstract List<Room> getAllRooms(String locale, Orderable orderable, Pageable pageable);

    public abstract Room getRoomById(Long id, String locale);

    public abstract List<RoomClass> getAllRoomClasses(String locale);

    public abstract RoomExtendedInfo getExtendedRoomInfoById(Long id, String locale);

    public abstract OverlapCountDTO getDatesOverlapCount(LocalDate checkInDate, LocalDate checkOutDate, Long roomId);

    public abstract List<RoomHistoryDTO> getRoomHistory(Long userId, String locale, Pageable pageable);

    public abstract List<Room> findSuitableRoomsForDates(String locale, LocalDate checkInDate, LocalDate checkOutDate, Orderable orderable, Pageable pageable);

    public abstract List<RoomRegistryPdfReportDto> findDataForRoomRegistryReport(LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable);

    public abstract boolean removeAssignedRoomsOnOverlappingDates(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    /**
     * Sets archived = true, for all old room registries
     */
    public abstract int archiveOldRoomRegistries();

    /**
     * Updates all rooms statuses, sets free if free for today date, sets occupied if room is occupied for today
     */
    public abstract int updateRoomStatus();

    /**
     * Closes given room (sets status = unavailable)
     * @param endDate Approximate date of room closing, money will be refunded for all people,
     * which ordered on the overlapping dates of today - endDate
     */
    public abstract boolean setRoomUnavailableAndRefundMoney(Long roomId, LocalDate endDate);

    public RoomsDao(Connection connection) {
        super(connection);
    }

}