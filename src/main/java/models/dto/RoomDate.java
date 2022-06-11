package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.time.LocalDate;

public class RoomDate {

    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    private LocalDate checkInDate;
    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private LocalDate checkOutDate;

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
}
