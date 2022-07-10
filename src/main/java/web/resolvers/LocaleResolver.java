package web.resolvers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.LocaleUtils;
import web.base.argument.resolvers.WebMethodArgumentResolver;

import java.lang.reflect.Parameter;
import java.util.Locale;

public class LocaleResolver implements WebMethodArgumentResolver<Locale> {

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Object previousResolved, Parameter methodParam) {
        return new Locale(LocaleUtils.getLocaleFromCookies(request.getCookies()));
    }
}
