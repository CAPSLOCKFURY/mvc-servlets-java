package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import validators.annotations.NotEmpty;
import validators.annotations.Regex;

public class ChangePasswordForm extends Form {

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "oldPassword", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullPassword")
    @Regex(pattern = "[a-zA-Z_0-9-]+", localizedError = "errors.passwordRegex")
    private String oldPassword;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "newPassword", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullPassword")
    @Regex(pattern = "[a-zA-Z_0-9-]+", localizedError = "errors.passwordRegex")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
