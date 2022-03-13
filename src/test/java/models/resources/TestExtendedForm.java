package models.resources;

import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.sql.Date;

public class TestExtendedForm {

    @SqlColumn(columnName = "", type = SqlType.STRING)
    private String stringField;

    @SqlColumn(columnName = "", type = SqlType.INT)
    private Integer intField;

    @SqlColumn(columnName = "", type = SqlType.LONG)
    private Long longField;

    @SqlColumn(columnName = "", type = SqlType.DECIMAL)
    private BigDecimal decimalField;

    @SqlColumn(columnName = "", type = SqlType.DATE)
    private java.sql.Date dateField;

    @SqlColumn(columnName = "", type = SqlType.BOOLEAN)
    private Boolean booleanField;

    public TestExtendedForm(String stringField, Integer intField, Long longField, BigDecimal decimalField, Date dateField, Boolean booleanField) {
        this.stringField = stringField;
        this.intField = intField;
        this.longField = longField;
        this.decimalField = decimalField;
        this.dateField = dateField;
        this.booleanField = booleanField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }

    public BigDecimal getDecimalField() {
        return decimalField;
    }

    public void setDecimalField(BigDecimal decimalField) {
        this.decimalField = decimalField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Boolean booleanField) {
        this.booleanField = booleanField;
    }
}
