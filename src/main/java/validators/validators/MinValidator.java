package validators.validators;

import validators.annotations.Min;
import validators.base.Validator;

public class MinValidator implements Validator<Min, Number> {
    @Override
    public boolean validate(Min annotation, Number value) {
        return value.floatValue() > annotation.min();
    }
}
