package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;

public class RoomDate {

    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    private java.sql.Date checkInDate;
    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private java.sql.Date checkOutDate;

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
}
