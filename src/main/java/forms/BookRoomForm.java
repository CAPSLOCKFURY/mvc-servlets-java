package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlLabel;
import utils.DateUtils;
import validators.annotations.FieldsNotEquals;
import validators.annotations.FirstDateBeforeSecond;
import validators.annotations.MinDateToday;
import validators.annotations.NotNull;

import java.time.LocalDate;

@FieldsNotEquals(
        firstField = "checkInDate",
        secondField = "checkOutDate",
        localizedError = "errors.CheckOutDateIsCheckInDate"
)
@FirstDateBeforeSecond(
        firstDateField = "checkInDate",
        secondDateField = "checkOutDate",
        localizedError = "errors.CheckOutDateBeforeCheckIn"
)
public class BookRoomForm extends Form {

    @HtmlInput(name = "checkInDate", type = InputType.DATE, id="checkInDate", literal = "onchange=\"dateChange()\" class=\"form-control my-1\"",
        label = @HtmlLabel(forElement = "checkInDate", localizedText = "checkInDate"))
    @NotNull(localizedError = "errors.checkInDateIAG")
    @MinDateToday(localizedError = "errors.CheckInDateInPast")
    private LocalDate checkInDate;

    @HtmlInput(name = "checkOutDate", type = InputType.DATE, id="checkOutDate", literal = "onchange=\"dateChange()\" class=\"form-control my-1\"",
        label = @HtmlLabel(forElement = "checkOutDate", localizedText = "checkOutDate"))
    @NotNull(localizedError = "errors.checkOutDateIAG")
    @MinDateToday(localizedError = "errors.CheckOutDateInPast")
    private LocalDate checkOutDate;

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        try {
            this.checkInDate = DateUtils.stringToDate(checkInDate);
        } catch (NullPointerException iag){
            this.checkInDate = null;
        }
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        try {
            this.checkOutDate = DateUtils.stringToDate(checkOutDate);
        } catch (NullPointerException iag){
            this.checkOutDate = null;
        }
    }
}
