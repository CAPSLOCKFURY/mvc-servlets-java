package validators.annotations;

import validators.base.ValidatedBy;
import validators.validators.FieldsEqualsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatedBy(FieldsEqualsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsEquals {

    String field1();

    String field2();

    String localizedError();

}
