package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.base.argument.resolvers.WebMethodArgumentResolver;
import web.resolvers.annotations.Form;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;

public class FormResolver implements WebMethodArgumentResolver<Form> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter parameter, Form annotation) {
        try {
            forms.base.Form form = (forms.base.Form) parameter.getType().getConstructor().newInstance();
            form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
            form.mapRequestToForm(request);
            return form;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
