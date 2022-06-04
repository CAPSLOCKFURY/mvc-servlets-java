package validators.base;

import exceptions.validators.ValidatorError;
import utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class ValidatorRegistry {

    private static final ValidatorRegistry instance = new ValidatorRegistry();

    private final Set<Class<? extends Annotation>> validatorAnnotations;

    private final Map<Class<? extends Annotation>, Class<?>> validatorMap;

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
             throw new ValidatorError("No validator registered for this annotation");
         }
         try {
             return (Validator<A, ?>)validatorClass.getConstructor().newInstance();
         } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
             e.printStackTrace();
             throw new ValidatorError("Error creating Validator class");
         }
    }

    public <A extends Annotation> boolean isAnnotationRegistered(A annotation){
        return validatorAnnotations.contains(annotation.annotationType());
    }

    @SuppressWarnings("unchecked")
    private Set<Class<? extends Annotation>> registerAnnotations(){
        List<Class<?>> annotationClassesList = ClassUtils.getClassesInPackage("validators.annotations", c -> c.isAnnotation() && c.isAnnotationPresent(ValidatedBy.class));
        Set<Class<?>> annotationSet = new HashSet<>(annotationClassesList);
        return (Set<Class<? extends Annotation>>)(Set<?>) annotationSet;
    }

    private Map<Class<? extends Annotation>, Class<?>> registerValidators(){
        Map<Class<? extends Annotation>, Class<?>> validatorMap = new HashMap<>();
        validatorAnnotations.forEach(a -> {
            ValidatedBy validatedBy = a.getAnnotation(ValidatedBy.class);
            Class<?> validatorClass = validatedBy.value();
            if(!Validator.class.isAssignableFrom(validatorClass)){
                throw new ValidatorError("Validator class does not implement Validator interface");
            }
            validatorMap.put(a, validatorClass);
        });
        return validatorMap;
    }

}
