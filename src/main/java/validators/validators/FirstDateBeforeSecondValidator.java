package validators.validators;

import exceptions.validators.ValidatorError;
import validators.annotations.FirstDateBeforeSecond;
import validators.base.Validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import static utils.StringUtils.getGetterMethod;

public class FirstDateBeforeSecondValidator implements Validator<FirstDateBeforeSecond, Object> {

    @Override
    public boolean validate(FirstDateBeforeSecond annotation, Object cls) {
        try {
            Method firstDateGetter = cls.getClass().getDeclaredMethod(getGetterMethod(annotation.firstDateField()));
            Method secondDateGetter = cls.getClass().getDeclaredMethod(getGetterMethod(annotation.secondDateField()));
            LocalDate firstDate = (LocalDate)firstDateGetter.invoke(cls);
            LocalDate secondDate = (LocalDate)secondDateGetter.invoke(cls);
            if(firstDate == null || secondDate == null){
                return false;
            }
            return firstDate.isBefore(secondDate);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ValidatorError("error in FirstDateBeforeSecondValidator for class: " + cls.getClass().getSimpleName());
        }
    }
}
