package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlLabel;
import forms.base.annotations.HtmlSelect;
import forms.base.annotations.HtmlTextArea;
import utils.DateUtils;
import validators.annotations.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@FirstDateBeforeSecond(
        firstDateField = "checkInDate",
        secondDateField = "checkOutDate",
        localizedError = "errors.CheckOutDateBeforeCheckIn"
)
@FieldsNotEquals(
        firstField = "checkInDate",
        secondField = "checkOutDate",
        localizedError = "errors.CheckOutDateIsCheckInDate"
)
public class RoomRequestForm extends Form {

    private Long userId;

    @HtmlInput(id = "capacity", name = "capacity", type = InputType.NUMBER, literal = "min =\"1\" class=\"form-control my-2\"",
            label = @HtmlLabel(forElement = "capacity", localizedText = "capacity"))
    @NotNull(localizedError = "errors.capacityNumberFormat")
    @Min(min = 0, localizedError = "errors.capacityNumberFormat")
    private Integer capacity;

    @HtmlSelect(id = "roomClass", name = "roomClass",
            dynamicOptionsAttribute = "roomClassesMap", literal = "class=\"form-select my-2\"",
            label = @HtmlLabel(forElement = "roomClass", localizedText = "roomClass")
    )
    private Integer roomClass;

    @HtmlInput(id = "checkInDate", name = "checkInDate", type = InputType.DATE, literal = "class=\"form-control my-2\"",
            label = @HtmlLabel(forElement = "checkInDate", localizedText = "checkInDate"))
    @NotNull(localizedError = "errors.checkInDateIAG")
    @MinDateToday(localizedError = "errors.CheckInDateInPast")
    private LocalDate checkInDate;

    @HtmlInput(id = "checkOutDate", name = "checkOutDate", type = InputType.DATE, literal = "class=\"form-control my-2\"",
            label = @HtmlLabel(forElement = "checkOutDate", localizedText = "checkOutDate"))
    @NotNull(localizedError = "errors.checkOutDateIAG")
    @MinDateToday(localizedError = "errors.CheckOutDateInPast")
    private LocalDate checkOutDate;

    @HtmlTextArea(id = "comment", rows = "3", cols = "35", name = "comment", literal = "class=\"form-control my-2\"",
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

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        try {
            this.checkInDate = DateUtils.stringToDate(checkInDate);
        } catch (NullPointerException | DateTimeParseException iag){
            this.checkInDate = null;
        }
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        try {
            this.checkOutDate = DateUtils.stringToDate(checkOutDate);
        } catch (NullPointerException | DateTimeParseException iag){
            this.checkOutDate = null;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
