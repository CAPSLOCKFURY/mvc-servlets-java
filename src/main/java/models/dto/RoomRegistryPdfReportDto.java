package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;
import java.time.LocalDate;

public class RoomRegistryPdfReportDto {

    @SqlColumn(columnName = "user_id", type = SqlType.LONG)
    private Long userId;

    @SqlColumn(columnName = "first_name", type = SqlType.STRING)
    private String firstName;

    @SqlColumn(columnName = "last_name", type = SqlType.STRING)
    private String lastName;

    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    private LocalDate checkInDate;

    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private LocalDate checkOutDate;

    @SqlColumn(columnName = "room_id", type = SqlType.LONG)
    private Long roomId;

    public RoomRegistryPdfReportDto(Long userId, String firstName, String lastName, LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {
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

    public LocalDate getCheckInDate() {return checkInDate;}

    public void setCheckInDate(LocalDate checkInDate) {this.checkInDate = checkInDate;}

    public LocalDate getCheckOutDate() {return checkOutDate;}

    public void setCheckOutDate(LocalDate checkOutDate) {this.checkOutDate = checkOutDate;}

    public Long getRoomId() {return roomId;}

    public void setRoomId(Long roomId) {this.roomId = roomId;}
}
