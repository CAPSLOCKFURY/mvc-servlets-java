package forms.resourses;

import forms.base.Form;
import forms.base.HtmlInput;
import forms.base.InputType;

public class TestForm extends Form {

    @HtmlInput(type = InputType.TEXT)
    private String testField;

    @HtmlInput(type = InputType.TEXT, name = "testName")
    private String testNameField;

    @HtmlInput(type = InputType.PASSWORD)
    private String stringField;

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

    @Override
    public String toString() {
        return "TestForm{" +
                "testField='" + testField + '\'' +
                ", testNameField='" + testNameField + '\'' +
                ", stringField='" + stringField + '\'' +
                '}';
    }
}
