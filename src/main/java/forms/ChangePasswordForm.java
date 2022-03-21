package forms;

import constants.RegexConstants;
import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;

import java.util.regex.Pattern;

public class ChangePasswordForm extends Form {

    private final static Pattern passwordPattern = RegexConstants.PASSWORD_PATTERN;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "oldPassword", literal = "class=\"form-control my-2\"")
    private String oldPassword;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "newPassword", literal = "class=\"form-control my-2\"")
    private String newPassword;

    @Override
    public boolean validate() {
        if(!oldPassword.equals("")) {
            if (!passwordPattern.matcher(oldPassword).matches()) {
                addLocalizedError("errors.passwordRegex");
            }
        } else {
            addLocalizedError("errors.nullPassword");
        }
        if(!newPassword.equals("")) {
            if (!passwordPattern.matcher(newPassword).matches()) {
                addLocalizedError("errors.passwordRegex");
            }
        } else {
            addLocalizedError("errors.nullPassword");
        }
        return errors.size() == 0;
    }

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
