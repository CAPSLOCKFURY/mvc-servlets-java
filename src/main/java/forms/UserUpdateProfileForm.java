package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import validators.annotations.NotEmpty;
import validators.annotations.Regex;

public class UserUpdateProfileForm extends Form {

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "firstName")
    @NotEmpty(localizedError = "errors.nullFirstName")
    @Regex(pattern = "[а-яА-Я|a-zA-Z]+", localizedError = "errors.lastNameRegex")
    private String firstName;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "lastName")
    @NotEmpty(localizedError = "errors.nullLastName")
    @Regex(pattern = "[а-яА-Я|a-zA-Z]+", localizedError = "errors.firstNameRegex")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
