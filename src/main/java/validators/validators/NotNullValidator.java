package validators.validators;

import validators.annotations.NotNull;
import validators.base.Validator;

public class NotNullValidator implements Validator<NotNull, Object> {
    @Override
    public boolean validate(NotNull annotation, Object value) {
        return value != null;
    }
}
