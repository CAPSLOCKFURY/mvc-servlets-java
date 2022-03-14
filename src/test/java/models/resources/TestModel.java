package models.resources;

import models.base.SqlColumn;
import models.base.SqlType;

import java.util.Objects;

public class TestModel {

    @SqlColumn(columnName = "field", type = SqlType.STRING)
    public String field;

    @SqlColumn(columnName = "intField", type = SqlType.INT)
    public Integer intField;

    @SqlColumn(columnName = "stringField", type = SqlType.STRING)
    public String field1;

    public TestModel(){

    }

    public TestModel(String field, Integer intField, String field1) {
        this.field = field;
        this.intField = intField;
        this.field1 = field1;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestModel testModel = (TestModel) o;
        return Objects.equals(field, testModel.field) && Objects.equals(intField, testModel.intField) && Objects.equals(field1, testModel.field1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, intField, field1);
    }
}
