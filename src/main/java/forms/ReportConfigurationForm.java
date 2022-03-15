package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;

import java.sql.Date;

public class ReportConfigurationForm extends Form {

    @HtmlInput(name = "page", type = InputType.NUMBER)
    private Integer page;

    @HtmlInput(name = "entitiesPerPage", type = InputType.NUMBER)
    private Integer entitiesPerPage;

    @HtmlInput(name = "checkInDate", type = InputType.DATE)
    private java.sql.Date checkInDate;

    @HtmlInput(name = "checkOutDate", type = InputType.DATE)
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
            checkInDate = null;
        }
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        try {
            this.checkOutDate = java.sql.Date.valueOf(checkOutDate);
        } catch (IllegalArgumentException iag){
            checkOutDate = null;
        }
    }

    @Override
    public boolean validate() {
        return true;
    }
}
