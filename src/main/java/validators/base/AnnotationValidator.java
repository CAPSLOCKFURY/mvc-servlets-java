package validators.base;

import exceptions.validators.ValidatorError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static utils.StringUtils.getGetterMethod;

public class AnnotationValidator {

    private final Object validationTarget;

    private static final ValidatorRegistry validatorRegistry = ValidatorRegistry.getInstance();

    private static final String LOCALIZED_ERROR_METHOD_NAME = "localizedError";

    private static final String IGNORE_ON_NULL = "ignoreOnNullValue";

    public AnnotationValidator(Object validationTarget){
        this.validationTarget = validationTarget;
    }

    public ValidationResult validate(){
        Class<?> targetClass = validationTarget.getClass();
        List<Annotation> objectAnnotations = Arrays.stream(targetClass.getAnnotations())
                .filter(validatorRegistry::isAnnotationRegistered)
                .collect(Collectors.toList());
        Map<Field, List<Annotation>> fieldAnnotations = getFieldAnnotations(targetClass);
        ValidationResult validationResult = new ValidationResult();
        validateClassAnnotations(objectAnnotations, validationResult);
        validateFieldAnnotations(fieldAnnotations, validationResult);
        return validationResult;
    }

    @SuppressWarnings("unchecked")
    private void validateClassAnnotations(List<Annotation> annotations, ValidationResult validationResult){
        for (Annotation a : annotations) {
            Validator validator = validatorRegistry.getValidatorByAnnotation(a);
            boolean result = executeValidator(validator, a, validationTarget);
            if(!result){
                validationResult.addLocalizedError(getLocalizedErrorFromAnnotation(a));
            }
        }
    }

    private void validateFieldAnnotations(Map<Field, List<Annotation>> fieldAnnotationMap, ValidationResult validationResult){
        for(Field field : fieldAnnotationMap.keySet()){
            List<Annotation> fieldAnnotations = fieldAnnotationMap.get(field);
            fieldAnnotations.forEach(a -> {
                try {
                    Method getter = validationTarget.getClass().getMethod(getGetterMethod(field.getName()));
                    Object fieldValue = getter.invoke(validationTarget);
                    boolean ignoreOnNullValue = getIgnoreOnNullValueFromAnnotation(a);
                    if(!(ignoreOnNullValue && fieldValue == null)) {
                        Validator validator = validatorRegistry.getValidatorByAnnotation(a);
                        boolean result = executeValidator(validator, a, fieldValue);
                        if (!result) {
                            validationResult.addLocalizedError(getLocalizedErrorFromAnnotation(a));
                        }
                    }
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new ValidatorError("Could not get getter method for field: " + field.getName());
                }
            });
        }
    }

    private String getLocalizedErrorFromAnnotation(Annotation a){
        try {
            Method method = a.annotationType().getDeclaredMethod(LOCALIZED_ERROR_METHOD_NAME);
            return (String)method.invoke(a);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ValidatorError("Could not get localized error from annotation: " + a.annotationType().getSimpleName());
        }
    }

    private boolean getIgnoreOnNullValueFromAnnotation(Annotation a) {
        try {
            Method method = a.annotationType().getDeclaredMethod(IGNORE_ON_NULL);
            return (boolean) method.invoke(a);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ValidatorError("Could not get ignore on null from annotation: " + a.annotationType().getSimpleName());
        } catch (NoSuchMethodException e){
            return false;
        }
    }

    private <A extends Annotation, V> boolean executeValidator(Validator<A, V> validator, A annotation, V value){
        validator.initialize(annotation);
        return validator.validate(annotation, value);
    }

    private Map<Field, List<Annotation>> getFieldAnnotations(Class<?> cls){
        List<Field> fields = Arrays.asList(cls.getDeclaredFields());
        return fields.stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        f -> Arrays.stream(f.getAnnotations())
                                .filter(validatorRegistry::isAnnotationRegistered).collect(Collectors.toList()))
                );
    }

}
