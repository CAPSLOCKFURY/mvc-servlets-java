package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlLabel;
import utils.DateUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReportConfigurationForm extends Form {

    @HtmlInput(id="page", name = "page", type = InputType.NUMBER, literal = "class=\"form-control\"",
            label = @HtmlLabel(forElement = "page", localizedText = "page"))
    private Integer page;

    @HtmlInput(id="epp", name = "entitiesPerPage", type = InputType.NUMBER, literal = "class=\"form-control\"", label = @HtmlLabel(forElement = "epp", localizedText = "epp"))
    private Integer entitiesPerPage;

    @HtmlInput(id="startDate", name = "checkInDate", type = InputType.DATE, literal = "class=\"form-control\"", label = @HtmlLabel(forElement = "startDate", localizedText = "startDate"))
    private LocalDate checkInDate;

    @HtmlInput(id="endDate", name = "checkOutDate", type = InputType.DATE, literal = "class=\"form-control\"", label = @HtmlLabel(forElement = "endDate", localizedText = "endDate"))
    private LocalDate checkOutDate;

    public Integer getPage() {
        return page;
    }

    public void setPage(String page) {
        try {
            this.page = Integer.parseInt(page);
        } catch (IllegalArgumentException iag){
            this.page = 1;
        }
    }

    public Integer getEntitiesPerPage() {
        return entitiesPerPage;
    }

    public void setEntitiesPerPage(String entitiesPerPage) {
        try {
            this.entitiesPerPage = Integer.parseInt(entitiesPerPage);
        } catch (IllegalArgumentException iag){
            this.entitiesPerPage = 10;
        }
    }

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

    @Override
    public boolean validate() {
        return true;
    }
}
