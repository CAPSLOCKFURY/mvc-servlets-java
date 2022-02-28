package forms.base;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Form {
    protected List<String> errors = new LinkedList<>();

    public List<String> getErrors() {
        return errors;
    }

    public final <T extends Form> void mapRequestToForm(T form, HttpServletRequest request){
        Class<? extends Form> formClass = form.getClass();
        Arrays.stream(formClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(HtmlInput.class))
                .forEach(field -> {
                    try {
                        HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
                        //TODO put this in another method
                        String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
                        Method method = formClass.getMethod("set".concat(field.getName().substring(0,1).toUpperCase().concat(field.getName().substring(1))), field.getType());
                        method.invoke(form, request.getParameter(name));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    public abstract boolean validate();

}
