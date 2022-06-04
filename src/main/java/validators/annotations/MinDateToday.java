package validators.annotations;

import validators.base.ValidatedBy;
import validators.validators.MinDateTodayValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatedBy(MinDateTodayValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDateToday {

    String localizedError();

    boolean ignoreOnNullValue() default true;
}
