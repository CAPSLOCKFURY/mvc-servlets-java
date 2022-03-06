package tags.resources;

import forms.base.Form;
import forms.base.annotations.HtmlInput;
import forms.base.InputType;

public class TagTestForm extends Form {

    @HtmlInput(type = InputType.TEXT)
    private String field;

    @HtmlInput(type = InputType.TEXT, placeholder = "field2 placeholder")
    private String field2;

    @HtmlInput(type = InputType.TEXT, name = "field3Name",placeholder = "field3 placeholder")
    private String field3;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
