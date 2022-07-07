package validators.base;

import exceptions.validators.ValidatorError;
import scanner.ClassPathScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class ValidatorRegistry {

    private static final ValidatorRegistry instance = new ValidatorRegistry();

    private final Set<Class<? extends Annotation>> validatorAnnotations;

    private final Map<Class<? extends Annotation>, Class<? extends Validator<?, ?>>> validatorMap;

    private ValidatorRegistry(){
        validatorAnnotations = registerAnnotations();
        validatorMap = registerValidators();
    }

    public static ValidatorRegistry getInstance(){
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation> Validator<A, ?> getValidatorByAnnotation(A a){
         Class<?> validatorClass = validatorMap.get(a.annotationType());
         if(validatorClass == null){
             throw new ValidatorError("No validator registered for this annotation: " + a.annotationType().getSimpleName());
         }
         try {
             return (Validator<A, ?>)validatorClass.getConstructor().newInstance();
         } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
             e.printStackTrace();
             throw new ValidatorError("Error creating Validator class: " + validatorClass.getSimpleName());
         }
    }

    public <A extends Annotation> boolean isAnnotationRegistered(A annotation){
        return validatorAnnotations.contains(annotation.annotationType());
    }

    @SuppressWarnings("unchecked")
    private Set<Class<? extends Annotation>> registerAnnotations(){
        ClassPathScanner classPathScanner = new ClassPathScanner();
        List<Class<?>> annotationClassesList = classPathScanner.scan("validators.annotations", c -> c.isAnnotation() && c.isAnnotationPresent(ValidatedBy.class));
        Set<Class<?>> annotationSet = new HashSet<>(annotationClassesList);
        return (Set<Class<? extends Annotation>>)(Set<?>) annotationSet;
    }

    @SuppressWarnings("unchecked")
    private Map<Class<? extends Annotation>, Class<? extends Validator<?, ?>>> registerValidators(){
        Map<Class<? extends Annotation>, Class<? extends Validator<?, ?>>> validatorMap = new HashMap<>();
        validatorAnnotations.forEach(a -> {
            ValidatedBy validatedBy = a.getAnnotation(ValidatedBy.class);
            Class<?> validatorClass = validatedBy.value();
            if(!Validator.class.isAssignableFrom(validatorClass)){
                throw new ValidatorError("Validator class does not implement Validator interface: " + validatorClass.getSimpleName());
            }
            validatorMap.put(a, (Class<? extends Validator<?, ?>>) validatorClass);
        });
        return validatorMap;
    }

}
