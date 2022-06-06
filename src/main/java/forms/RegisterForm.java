package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import validators.annotations.FieldsEquals;
import validators.annotations.NotEmpty;
import validators.annotations.Regex;

@FieldsEquals(
        field1 = "password",
        field2 = "repeatPassword",
        localizedError = "errors.repeatPassword"
)
public class RegisterForm extends Form {

    @HtmlInput(id = "login", type = InputType.TEXT, localizedPlaceholder = "login", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullLogin")
    @Regex(pattern = "[a-zA-Z_0-9-]+", localizedError = "errors.loginRegex")
    private String login;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "email", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullEmail")
    @Regex(pattern = "[a-z0-9.]+@[a-z]+(\\.com|\\.net|\\.ukr|\\.ru|\\.ua)", localizedError = "errors.emailRegex")
    private String email;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "password", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullPassword")
    @Regex(pattern = "[a-zA-Z_0-9-]+", localizedError = "errors.passwordRegex")
    private String password;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "repeatPassword", literal = "class=\"form-control my-2\"")
    private String repeatPassword;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "firstName", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullFirstName")
    @Regex(pattern = "[а-яА-Я|a-zA-Z]+", localizedError = "errors.firstNameRegex")
    private String firstName;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "lastName", literal = "class=\"form-control my-2\"")
    @NotEmpty(localizedError = "errors.nullLastName")
    @Regex(pattern = "[а-яА-Я|a-zA-Z]+", localizedError = "errors.lastNameRegex")
    private String lastName;

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
