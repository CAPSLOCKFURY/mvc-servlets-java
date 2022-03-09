package models;

import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;

public class RoomRequest {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;

    @SqlColumn(columnName = "capacity", type = SqlType.INT)
    private Integer capacity;

    @SqlColumn(columnName = "class_name", type = SqlType.STRING)
    private String roomClass;

    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    private java.sql.Date checkInDate;

    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private java.sql.Date checkOutDate;

    @SqlColumn(columnName = "comment", type = SqlType.STRING)
    private String comment;

    @SqlColumn(columnName = "status", type = SqlType.STRING)
    private String status;

    @SqlColumn(columnName = "room_id", type = SqlType.LONG)
    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
