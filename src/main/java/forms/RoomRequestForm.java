package forms;

import forms.base.Form;
import forms.base.annotations.*;
import forms.base.InputType;
import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;

public class RoomRequestForm extends Form {

    @SqlColumn(columnName = "user_id", type = SqlType.LONG)
    private Long userId;

    @SqlColumn(columnName = "capacity", type = SqlType.INT)
    @HtmlInput(id = "capacity", name = "capacity", type = InputType.NUMBER,
            label = @HtmlLabel(forElement = "capacity", localizedText = "capacity"))
    private Integer capacity;

    @SqlColumn(columnName = "room_class", type = SqlType.INT)
    @HtmlSelect(id = "roomClass", name = "roomClass",
            dynamicOptionsAttribute = "roomClassesMap",
            label = @HtmlLabel(forElement = "roomClass", localizedText = "roomClass")
    )
    private Integer roomClass;

    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    @HtmlInput(id = "checkInDate", name = "checkInDate", type = InputType.DATE,
            label = @HtmlLabel(forElement = "checkInDate", localizedText = "checkInDate"))
    private java.sql.Date checkInDate;

    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    @HtmlInput(id = "checkOutDate", name = "checkOutDate", type = InputType.DATE,
            label = @HtmlLabel(forElement = "checkOutDate", localizedText = "checkOutDate"))
    private java.sql.Date checkOutDate;

    @SqlColumn(columnName = "comment", type = SqlType.STRING)
    @HtmlTextArea(id = "comment", rows = "3", cols = "35", name = "comment",
            label = @HtmlLabel(forElement = "comment", localizedText = "comment"))
    private String comment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = Integer.parseInt(capacity);
    }

    public Integer getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = Integer.parseInt(roomClass);
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
