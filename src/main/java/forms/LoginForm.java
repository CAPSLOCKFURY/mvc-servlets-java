package forms;

import constants.RegexConstants;
import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import validators.annotations.NotEmpty;

import java.util.regex.Pattern;

public class LoginForm extends Form {

    private static final Pattern loginPattern = RegexConstants.LOGIN_PATTERN;
    private static final Pattern passwordPattern = RegexConstants.PASSWORD_PATTERN;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "login", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullLogin")
    private String login;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "password", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullPassword")
    private String password;

    @Override
    public boolean validate() {
        super.validate();
        if(!login.equals("")) {
            if (!loginPattern.matcher(login).matches()) {
                addLocalizedError("errors.loginRegex");
            }
        }
        if(!password.equals("")) {
            if (!passwordPattern.matcher(password).matches()) {
                addLocalizedError("errors.passwordRegex");
            }
        }
        return errors.size() == 0;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
