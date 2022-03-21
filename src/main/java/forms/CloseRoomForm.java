package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;

import java.sql.Date;
import java.time.LocalDate;

public class CloseRoomForm extends Form {

    @HtmlInput(name = "endDate", type = InputType.DATE, id = "endDate", literal = "class=\"form-control my-2\"")
    private java.sql.Date endDate;

    @Override
    public boolean validate() {
        //TODO remove duplicated code
        LocalDate today = new Date(System.currentTimeMillis()).toLocalDate();
        if(endDate != null){
            LocalDate checkInLocalDate = endDate.toLocalDate();
            if (checkInLocalDate.isBefore(today)) {
                addLocalizedError("errors.CheckOutDateInPast");
            }
        }
        return errors.size() == 0;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        try {
            this.endDate = java.sql.Date.valueOf(endDate);
        } catch (IllegalArgumentException iag){
            addLocalizedError("errors.endDateIsNull");
        }
    }
}
