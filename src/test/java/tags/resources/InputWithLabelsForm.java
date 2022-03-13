package tags.resources;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlLabel;

public class InputWithLabelsForm extends Form {

    @HtmlInput(id = "id", name = "input", type = InputType.TEXT, label =
    @HtmlLabel(forElement = "id", text = "label"))
    private String input;

    @Override
    public boolean validate() {
        return true;
    }
}
