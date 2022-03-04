package forms.base;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
                .filter(field -> field.isAnnotationPresent(HtmlInput.class))
                .forEach(field -> {
                    try {
                        HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
                        //TODO put this in another method
                        String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
                        // TODO use capitalize method
                        Method method = formClass.getMethod("set".concat(field.getName().substring(0,1).toUpperCase().concat(field.getName().substring(1))), field.getType());
                        method.invoke(this, request.getParameter(name));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void setLocale(Locale locale){
        this.locale = locale;
    }
}
