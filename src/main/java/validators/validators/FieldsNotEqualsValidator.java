package validators.validators;

import exceptions.validators.ValidatorError;
import validators.annotations.FieldsNotEquals;
import validators.base.Validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static utils.StringUtils.getGetterMethod;

public class FieldsNotEqualsValidator implements Validator<FieldsNotEquals, Object> {

    @Override
    public boolean validate(FieldsNotEquals annotation, Object cls) {
        try {
            Method firstFieldGetter = cls.getClass().getMethod(getGetterMethod(annotation.firstField()));
            Method secondFieldGetter = cls.getClass().getMethod(getGetterMethod(annotation.secondField()));
            Object first = firstFieldGetter.invoke(cls);
            Object second = secondFieldGetter.invoke(cls);
            if(first != null && second != null) {
                return !first.equals(second);
            }
            return false;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ValidatorError("Error in FieldsNotEqualsValidator for class: " + cls.getClass().getSimpleName());
        }
    }
}
