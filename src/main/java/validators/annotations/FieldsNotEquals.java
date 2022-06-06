package validators.annotations;

import validators.base.ValidatedBy;
import validators.validators.FieldsNotEqualsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatedBy(FieldsNotEqualsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsNotEquals {

    String firstField();

    String secondField();

    String localizedError();
}
