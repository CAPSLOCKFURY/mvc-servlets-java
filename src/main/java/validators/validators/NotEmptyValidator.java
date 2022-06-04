package validators.validators;

import validators.annotations.NotEmpty;
import validators.base.Validator;

public class NotEmptyValidator implements Validator<NotEmpty, String> {

    @Override
    public boolean validate(NotEmpty annotation, String value) {
        return !value.isEmpty();
    }
}
