package models.resources;

import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class ExtendedTestModel {

    @SqlColumn(columnName = "stringField", type = SqlType.STRING)
    private String stringField;

    @SqlColumn(columnName = "intField", type = SqlType.INT)
    private Integer intField;

    @SqlColumn(columnName = "longField", type = SqlType.LONG)
    private Long longField;

    @SqlColumn(columnName = "decimalField", type = SqlType.DECIMAL)
    private BigDecimal decimalField;

    @SqlColumn(columnName = "dateField", type = SqlType.DATE)
    private java.sql.Date dateField;

    @SqlColumn(columnName = "booleanField", type = SqlType.BOOLEAN)
    private Boolean booleanField;

    public ExtendedTestModel(){

    }

    public ExtendedTestModel(String stringField, Integer intField, Long longField, BigDecimal decimalField, Date dateField, Boolean booleanField) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedTestModel that = (ExtendedTestModel) o;
        return Objects.equals(stringField, that.stringField) && Objects.equals(intField, that.intField) && Objects.equals(longField, that.longField) && Objects.equals(decimalField, that.decimalField) && Objects.equals(dateField, that.dateField) && Objects.equals(booleanField, that.booleanField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringField, intField, longField, decimalField, dateField, booleanField);
    }
}
