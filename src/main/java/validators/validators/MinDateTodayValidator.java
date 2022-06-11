package validators.validators;

import validators.annotations.MinDateToday;
import validators.base.Validator;

import java.sql.Date;
import java.time.LocalDate;

public class MinDateTodayValidator implements Validator<MinDateToday, LocalDate> {
    @Override
    public boolean validate(MinDateToday annotation, LocalDate value) {
        LocalDate today = new Date(System.currentTimeMillis()).toLocalDate();
        return !value.isBefore(today);
    }
}
