package models.resources;

import models.base.SqlRow;
import models.base.SqlType;

public class TestModel extends AbstractTestModel {

    @SqlRow(rowName = "field", type = SqlType.STRING)
    public String field;

    @SqlRow(rowName = "intField", type = SqlType.INT)
    public Integer intField;

    @SqlRow(rowName = "stringField", type = SqlType.STRING)
    public String field1;

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
    public String toString() {
        return "TestModel{" +
                "field='" + field + '\'' +
                ", intField='" + intField + '\'' +
                ", field1='" + field1 + '\'' +
                '}';
    }
}
