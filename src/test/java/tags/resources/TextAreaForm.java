package tags.resources;

import forms.base.Form;
import forms.base.annotations.HtmlTextArea;

public class TextAreaForm extends Form {

    @HtmlTextArea(name = "area", rows = "10", cols = "10")
    private String area;

    @Override
    public boolean validate() {
        return true;
    }
}
