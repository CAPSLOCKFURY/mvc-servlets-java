package tags.resources;

import forms.base.*;

public class TestSelectForm extends Form {

    @HtmlInput(type = InputType.TEXT)
    private String testField;

    @HtmlSelect(name = "select", options = {@HtmlOption(name = "1", value = "One"), @HtmlOption(name = "2", value = "Second")})
    private String select;

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public String toString() {
        return "TestSelectForm{" +
                "testField='" + testField + '\'' +
                ", select='" + select + '\'' +
                '}';
    }
}
