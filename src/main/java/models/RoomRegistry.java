package models;

import models.base.SqlColumn;
import models.base.SqlType;

import java.time.LocalDate;

public class RoomRegistry {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;

    @SqlColumn(columnName = "user_id", type = SqlType.LONG)
    private Long userId;

    @SqlColumn(columnName = "room_id", type = SqlType.LONG)
    private Long roomId;

    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    private LocalDate checkInDate;

    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private LocalDate checkOutDate;

    @SqlColumn(columnName = "archived", type = SqlType.BOOLEAN)
    private Boolean archived = false;

    public RoomRegistry(Long userId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public RoomRegistry(Long id, Long userId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Boolean archived) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.archived = archived;
    }

    public RoomRegistry() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
