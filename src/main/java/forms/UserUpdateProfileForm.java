package forms;

import constants.RegexConstants;
import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import models.base.SqlColumn;
import models.base.SqlType;

import java.util.regex.Pattern;

public class UserUpdateProfileForm extends Form {

    private final static Pattern namePattern = RegexConstants.NAME_PATTERN;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "firstName")
    @SqlColumn(columnName = "first_name", type = SqlType.STRING)
    private String firstName;

    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "lastName")
    @SqlColumn(columnName = "last_name", type = SqlType.STRING)
    private String lastName;

    @Override
    public boolean validate() {
        //TODO remove duplicates
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
