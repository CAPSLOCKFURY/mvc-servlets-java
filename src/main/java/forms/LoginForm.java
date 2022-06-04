package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import validators.annotations.NotEmpty;
import validators.annotations.Regex;

public class LoginForm extends Form {

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "login", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullLogin")
    @Regex(pattern = "[a-zA-Z_0-9-]+", localizedError = "errors.loginRegex")
    private String login;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "password", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullPassword")
    @Regex(pattern = "[a-zA-Z_0-9-]+", localizedError = "errors.passwordRegex")
    private String password;

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
