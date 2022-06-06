package validators.annotations;


import validators.base.ValidatedBy;
import validators.validators.FirstDateBeforeSecondValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatedBy(FirstDateBeforeSecondValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstDateBeforeSecond {

    String firstDateField();

    String secondDateField();

    String localizedError();

}
