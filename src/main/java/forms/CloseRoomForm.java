package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import validators.annotations.MinDateToday;
import validators.annotations.NotNull;

import java.sql.Date;

public class CloseRoomForm extends Form {

    @HtmlInput(name = "endDate", type = InputType.DATE, id = "endDate", literal = "class=\"form-control my-2\"")
    @NotNull(localizedError = "errors.endDateIsNull")
    @MinDateToday(localizedError = "errors.CheckOutDateInPast")
    private java.sql.Date endDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        try {
            this.endDate = java.sql.Date.valueOf(endDate);
        } catch (IllegalArgumentException iag){
            this.endDate = null;
        }
    }
}
