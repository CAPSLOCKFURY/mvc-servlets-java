package validators.validators;

import validators.RegexCache;
import validators.annotations.Regex;
import validators.base.Validator;

import java.util.regex.Pattern;

public class RegexValidator implements Validator<Regex, String> {

    @Override
    public boolean validate(Regex annotation, String value) {
        Pattern pattern = RegexCache.getPattern(value);
        return pattern.matcher(value).matches();
    }
}
