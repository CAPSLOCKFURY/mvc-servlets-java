package tags.resources;

import forms.base.Form;
import forms.base.annotations.HtmlInput;
import forms.base.InputType;

public class LocalizedTestForm extends Form {

    @HtmlInput(type = InputType.TEXT, name = "login", localizedPlaceholder = "renderer_form_test.login")
    private String login;

    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "renderer_form_test.password")
    private String password;

    @HtmlInput(type = InputType.TEXT, placeholder = "some non localized field")
    private String someField;

    @Override
    public boolean validate() {
        return true;
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

    public String getSomeField() {
        return someField;
    }

    public void setSomeField(String someField) {
        this.someField = someField;
    }
}
