package forms;

import forms.base.Form;
import forms.base.annotations.*;
import forms.base.InputType;
import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;

public class RoomRequestForm extends Form {

    @SqlColumn(columnName = "capacity", type = SqlType.INT)
    @HtmlInput(id = "capacity", name = "capacity", type = InputType.NUMBER,
            label = @HtmlLabel(forElement = "capacity", localizedText = "capacity"))
    private Integer capacity;

    @SqlColumn(columnName = "room_class", type = SqlType.STRING)
    @HtmlSelect(name = "roomClass", options = {
            @HtmlOption(name = "Cheap", value = "Cheap"),
            @HtmlOption(name = "Normal", value = "Normal"),
            @HtmlOption(name = "Half lux", value = "Half lux"),
            @HtmlOption(name = "Lux", value = "Lux")
    })
    private String roomClass;

    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    @HtmlInput(name = "checkInDate", type = InputType.DATE)
    private java.sql.Date checkInDate;

    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    @HtmlInput(name = "checkOutDate", type = InputType.DATE)
    private java.sql.Date checkOutDate;

    @SqlColumn(columnName = "comment", type = SqlType.STRING)
    @HtmlTextArea(rows = "3", cols = "35", name = "comment")
    private String comment;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = Integer.parseInt(capacity);
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

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = Date.valueOf(checkInDate);
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = Date.valueOf(checkOutDate);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
