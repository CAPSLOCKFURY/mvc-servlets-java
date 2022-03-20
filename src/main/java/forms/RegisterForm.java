package forms;

import constants.RegexConstants;
import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import models.base.SqlColumn;
import models.base.SqlType;

import java.util.regex.Pattern;

public class RegisterForm extends Form {
    private final static Pattern loginPattern = RegexConstants.LOGIN_PATTERN;
    private final static Pattern emailPattern = RegexConstants.EMAIL_PATTERN;
    private final static Pattern passwordPattern = RegexConstants.PASSWORD_PATTERN;
    private final static Pattern namePattern = RegexConstants.NAME_PATTERN;

    @HtmlInput(id = "login", type = InputType.TEXT, localizedPlaceholder = "login", literal = "class=\"form-control my-2\"")
    @SqlColumn(columnName = "login", type = SqlType.STRING)
    private String login;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "email", literal = "class=\"form-control my-2\"")
    @SqlColumn(columnName = "email", type = SqlType.STRING)
    private String email;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "password", literal = "class=\"form-control my-2\"")
    @SqlColumn(columnName = "password", type = SqlType.STRING)
    private String password;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "repeatPassword", literal = "class=\"form-control my-2\"")
    private String repeatPassword;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "firstName", literal = "class=\"form-control my-2\"")
    @SqlColumn(columnName = "first_name", type = SqlType.STRING)
    private String firstName;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "lastName", literal = "class=\"form-control my-2\"")
    @SqlColumn(columnName = "last_name", type = SqlType.STRING)
    private String lastName;


    @Override
    public boolean validate() {
        if(!password.equals("")) {
            if (!passwordPattern.matcher(password).matches()) {
                addLocalizedError("errors.passwordRegex");
            }
            if (!password.equals(repeatPassword)) {
                addLocalizedError("errors.repeatPassword");
            }
        } else {
            addLocalizedError("errors.nullPassword");
        }
        if(!login.equals("")) {
            if (!loginPattern.matcher(login).matches()) {
                addLocalizedError("errors.loginRegex");
            }
        } else {
            addLocalizedError("errors.nullLogin");
        }
        if(!email.equals("")) {
            if (!emailPattern.matcher(email).matches()) {
                addLocalizedError("errors.emailRegex");
            }
        } else {
            addLocalizedError("errors.nullEmail");
        }
        if(!firstName.equals("")) {
            if (!namePattern.matcher(firstName).matches()) {
                addLocalizedError("errors.firstNameRegex");
            }
        } else {
            addLocalizedError("errors.nullFirstName");
        }
        if(!lastName.equals("")) {
            if (!namePattern.matcher(lastName).matches()) {
                addLocalizedError("errors.lastNameRegex");
            }
        } else {
            addLocalizedError("errors.nullLastName");
        }
        return errors.size() == 0;
    }

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
}
