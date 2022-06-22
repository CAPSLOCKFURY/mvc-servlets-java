package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import utils.DateUtils;
import validators.annotations.MinDateToday;
import validators.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CloseRoomForm extends Form {

    @HtmlInput(name = "endDate", type = InputType.DATE, id = "endDate", literal = "class=\"form-control my-2\"")
    @NotNull(localizedError = "errors.endDateIsNull")
    @MinDateToday(localizedError = "errors.CheckOutDateInPast")
    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        try {
            this.endDate = DateUtils.stringToDate(endDate);
        } catch (NullPointerException | DateTimeParseException iag){
            this.endDate = null;
        }
    }
}
