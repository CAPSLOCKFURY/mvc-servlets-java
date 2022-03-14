package forms.resourses;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlOption;
import forms.base.annotations.HtmlSelect;
import forms.base.annotations.HtmlTextArea;

public class TestForm extends Form {

    @HtmlInput(type = InputType.TEXT)
    private String testField;

    @HtmlTextArea(name = "testName", rows = "100", cols = "200")
    private String testNameField;

    @HtmlInput(type = InputType.PASSWORD)
    private String stringField;

    @HtmlSelect(name = "selectField", options = {
            @HtmlOption(value = "1", name = "1"),
            @HtmlOption(value = "2", name = "2"),
            @HtmlOption(value = "3", name = "3")
    })
    private String selectField;

    @Override
    public boolean validate() {
        return true;
    }

    public String getTestField() {
        return testField;
    }

    public void setTestField(String testField) {
        this.testField = testField;
    }

    public String getTestNameField() {
        return testNameField;
    }

    public void setTestNameField(String testNameField) {
        this.testNameField = testNameField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public String getSelectField() {
        return selectField;
    }

    public void setSelectField(String selectField) {
        this.selectField = selectField;
    }

    @Override
    public String toString() {
        return "TestForm{" +
                "testField='" + testField + '\'' +
                ", testNameField='" + testNameField + '\'' +
                ", stringField='" + stringField + '\'' +
                ", selectField='" + selectField + '\'' +
                '}';
    }
}
