package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;

public class RoomRegistryPdfReportDto {

    @SqlColumn(columnName = "userId", type = SqlType.LONG)
    private Long userId;

    @SqlColumn(columnName = "firstName", type = SqlType.STRING)
    private String firstName;

    @SqlColumn(columnName = "lastName", type = SqlType.STRING)
    private String lastName;

    @SqlColumn(columnName = "checkInDate", type = SqlType.DATE)
    private java.sql.Date checkInDate;

    @SqlColumn(columnName = "checkOutDate", type = SqlType.DATE)
    private java.sql.Date checkOutDate;

    @SqlColumn(columnName = "roomId", type = SqlType.LONG)
    private Long roomId;

    public RoomRegistryPdfReportDto(Long userId, String firstName, String lastName, Date checkInDate, Date checkOutDate, Long roomId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomId = roomId;
    }

    public RoomRegistryPdfReportDto() {
    }

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {this.userId = userId;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public Date getCheckInDate() {return checkInDate;}

    public void setCheckInDate(Date checkInDate) {this.checkInDate = checkInDate;}

    public Date getCheckOutDate() {return checkOutDate;}

    public void setCheckOutDate(Date checkOutDate) {this.checkOutDate = checkOutDate;}

    public Long getRoomId() {return roomId;}

    public void setRoomId(Long roomId) {this.roomId = roomId;}
}
