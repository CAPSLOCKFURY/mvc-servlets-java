package forms.base;

import forms.base.annotations.HtmlInput;
import forms.base.annotations.HtmlSelect;
import forms.base.annotations.HtmlTextArea;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static utils.StringUtils.capitalize;

public abstract class Form {
    protected List<String> errors = new LinkedList<>();

    protected Locale locale = Locale.ROOT;

    public abstract boolean validate();

    public List<String> getErrors() {
        return errors;
    }

    public final void mapRequestToForm(HttpServletRequest request){
        Class<? extends Form> formClass = this.getClass();
        Arrays.stream(formClass.getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(HtmlInput.class)
                                || field.isAnnotationPresent(HtmlSelect.class)
                                || field.isAnnotationPresent(HtmlTextArea.class))
                .forEach(field -> {
                    try {
                        HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
                        //TODO put this in another method
                        String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
                        Method method = formClass.getMethod("set".concat(capitalize(field.getName())), String.class);
                        method.invoke(this, request.getParameter(name));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

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
