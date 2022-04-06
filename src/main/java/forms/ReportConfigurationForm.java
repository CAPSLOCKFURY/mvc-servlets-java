package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlLabel;

import java.sql.Date;

public class ReportConfigurationForm extends Form {

    @HtmlInput(id="page", name = "page", type = InputType.NUMBER, literal = "class=\"form-control\"",
            label = @HtmlLabel(forElement = "page", localizedText = "page"))
    private Integer page;

    @HtmlInput(id="epp", name = "entitiesPerPage", type = InputType.NUMBER, literal = "class=\"form-control\"", label = @HtmlLabel(forElement = "epp", localizedText = "epp"))
    private Integer entitiesPerPage;

    @HtmlInput(id="startDate", name = "checkInDate", type = InputType.DATE, literal = "class=\"form-control\"", label = @HtmlLabel(forElement = "startDate", localizedText = "startDate"))
    private java.sql.Date checkInDate;

    @HtmlInput(id="endDate", name = "checkOutDate", type = InputType.DATE, literal = "class=\"form-control\"", label = @HtmlLabel(forElement = "endDate", localizedText = "endDate"))
    private java.sql.Date checkOutDate;

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

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        try {
            this.checkInDate = java.sql.Date.valueOf(checkInDate);
        } catch (IllegalArgumentException iag){
            this.checkInDate = null;
        }
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        try {
            this.checkOutDate = java.sql.Date.valueOf(checkOutDate);
        } catch (IllegalArgumentException iag){
            this.checkOutDate = null;
        }
    }

    @Override
    public boolean validate() {
        return true;
    }
}
