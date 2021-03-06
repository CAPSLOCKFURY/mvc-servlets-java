package forms.base;

import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlSelect;
import forms.base.annotations.HtmlTextArea;
import jakarta.servlet.http.HttpServletRequest;
import validators.base.AnnotationValidator;
import validators.base.ValidationResult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static utils.StringUtils.capitalize;

/**
 * Base class for all forms
 */
public abstract class Form {
    protected List<String> errors = new LinkedList<>();

    protected Locale locale = Locale.ROOT;

    public boolean validate(){
        AnnotationValidator annotationValidator = new AnnotationValidator(this);
        ValidationResult validationResult = annotationValidator.validate();
        validationResult.getLocalizedErrors().forEach(this::addLocalizedError);
        return isValid();
    }

    public List<String> getErrors() {
        return errors;
    }

    /**
     * Maps request parameters to html form with {@link HtmlInput} or {@link HtmlSelect} or {@link HtmlTextArea} annotation
     * <p>
     *     Note: form must have String setter for each field which should be mapped
     * </p>
     * @param request Request from which parameters will be got
     */
    public final void mapRequestToForm(HttpServletRequest request){
        Class<? extends Form> formClass = this.getClass();
        Arrays.stream(formClass.getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(HtmlInput.class)
                                || field.isAnnotationPresent(HtmlSelect.class)
                                || field.isAnnotationPresent(HtmlTextArea.class))
                .forEach(field -> {
                    try {
                        String name = getFieldName(field);
                        Method method = formClass.getMethod("set".concat(capitalize(field.getName())), String.class);
                        method.invoke(this, request.getParameter(name));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

    }

    private String getFieldName(Field field){
        if(field.isAnnotationPresent(HtmlInput.class)){
            HtmlInput input = field.getDeclaredAnnotation(HtmlInput.class);
            return input.name().equals("") ? field.getName() : input.name();
        }
        if(field.isAnnotationPresent(HtmlSelect.class)){
            HtmlSelect input = field.getDeclaredAnnotation(HtmlSelect.class);
            return input.name().equals("") ? field.getName() : input.name();
        }
        if(field.isAnnotationPresent(HtmlTextArea.class)){
            HtmlTextArea input = field.getDeclaredAnnotation(HtmlTextArea.class);
            return input.name().equals("") ? field.getName() : input.name();
        }
        return "";
    }

    public boolean isValid(){
        return errors.size() == 0;
    }

    public boolean addError(String error){
        return errors.add(error);
    }

    public boolean addLocalizedError(String errorKey){
        ResourceBundle bundle = ResourceBundle.getBundle("forms", locale);
        return errors.add(bundle.getString(errorKey));
    }

    public void setLocale(Locale locale){
        this.locale = locale;
    }
}
