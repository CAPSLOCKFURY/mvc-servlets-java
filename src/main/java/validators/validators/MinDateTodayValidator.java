package validators.validators;

import validators.annotations.MinDateToday;
import validators.base.Validator;

import java.sql.Date;
import java.time.LocalDate;

public class MinDateTodayValidator implements Validator<MinDateToday, java.sql.Date> {
    @Override
    public boolean validate(MinDateToday annotation, Date value) {
        LocalDate date = value.toLocalDate();
        LocalDate today = new Date(System.currentTimeMillis()).toLocalDate();
        return !date.isBefore(today);
    }
}
