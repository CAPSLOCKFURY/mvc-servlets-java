package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlLabel;
import forms.base.annotations.HtmlSelect;
import forms.base.annotations.HtmlTextArea;
import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;
import java.time.LocalDate;

public class RoomRequestForm extends Form {

    @SqlColumn(columnName = "user_id", type = SqlType.LONG)
    private Long userId;

    @SqlColumn(columnName = "capacity", type = SqlType.INT)
    @HtmlInput(id = "capacity", name = "capacity", type = InputType.NUMBER, literal = "min =\"1\"",
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

    @Override
    public boolean validate() {
        LocalDate today = new Date(System.currentTimeMillis()).toLocalDate();
        if(checkInDate != null){
            LocalDate checkInLocalDate = checkInDate.toLocalDate();
            if (checkInLocalDate.isBefore(today)) {
                addLocalizedError("errors.CheckInDateInPast");
            }
        }
        if(checkOutDate != null){
            LocalDate checkOutLocalDate = checkOutDate.toLocalDate();
            if(checkOutLocalDate.isBefore(today)){
                addLocalizedError("errors.CheckOutDateInPast");
            }
        }
        if(checkOutDate != null && checkInDate != null) {
            if (checkOutDate.before(checkInDate)) {
                addLocalizedError("errors.CheckOutDateBeforeCheckIn");
            }
            if(checkOutDate.compareTo(checkInDate) == 0){
                addLocalizedError("errors.CheckOutDateIsCheckInDate");
            }
        }
        return errors.size() == 0;
    }

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
        try {
            this.capacity = Integer.parseInt(capacity);
        } catch (NumberFormatException nfe){
            addLocalizedError("errors.capacityNumberFormat");
            this.capacity = 0;
        }
    }

    public Integer getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        try {
            this.roomClass = Integer.parseInt(roomClass);
            if(this.roomClass <= 0){
                addLocalizedError("errors.roomClassNumberFormat");
            }
        } catch (NumberFormatException nfe){
            addLocalizedError("errors.roomClassNumberFormat");
            this.roomClass = 0;
        }
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        try {
            this.checkInDate = Date.valueOf(checkInDate);
        } catch (IllegalArgumentException iag){
            addLocalizedError("errors.checkInDateIAG");
            this.checkInDate = null;
        }
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        try {
            this.checkOutDate = Date.valueOf(checkOutDate);
        } catch (IllegalArgumentException iag){
            addLocalizedError("errors.checkOutDateIAG");
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
