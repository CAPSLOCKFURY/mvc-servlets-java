package validators.validators;

import exceptions.validators.ValidatorError;
import validators.annotations.FieldsEquals;
import validators.base.Validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static utils.StringUtils.getGetterMethod;

public class FieldsEqualsValidator implements Validator<FieldsEquals, Object> {
    @Override
    public boolean validate(FieldsEquals annotation, Object cls) {
        try {
            Method firstFieldGetter = cls.getClass().getMethod(getGetterMethod(annotation.field1()));
            Method secondFieldGetter = cls.getClass().getMethod(getGetterMethod(annotation.field2()));
            Object first = firstFieldGetter.invoke(cls);
            Object second = secondFieldGetter.invoke(cls);
            if(first != null && second != null) {
                return first.equals(second);
            }
            return false;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ValidatorError("Error in FieldsEqualsValidator for class: " + cls.getClass().getSimpleName());
        }
    }
}
