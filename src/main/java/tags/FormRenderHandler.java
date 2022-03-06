package tags;

import forms.base.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import utils.LocaleUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class FormRenderHandler extends TagSupport {

    private String formClassPath;

    public void setFormClassPath(String formClassPath){
        this.formClassPath = formClassPath;
    }

    private JspWriter out;

    @Override
    public int doStartTag(){
        out = pageContext.getOut();
        Class<?> formClass = classForName(formClassPath);
        if(formClass == null || !formClass.getSuperclass().equals(Form.class)){
            return SKIP_BODY;
        }
        Arrays.stream(formClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(HtmlInput.class) || field.isAnnotationPresent(HtmlSelect.class))
                .forEach(field -> {
                    if(field.isAnnotationPresent(HtmlInput.class)) {
                        write(renderInput(field));
                        return;
                    }
                    if(field.isAnnotationPresent(HtmlSelect.class)){
                        write(renderSelect(field));
                        return;
                    }
                });
        return SKIP_BODY;
    }

    private String renderSelect(Field field){
        HtmlSelect htmlSelect = field.getDeclaredAnnotation(HtmlSelect.class);
        Map<String, String> options = Arrays.stream(htmlSelect.options())
                .collect(Collectors.toMap(HtmlOption::value, HtmlOption::name));
        HtmlSelectRenderer renderer = new HtmlSelectRenderer.Builder()
                .withName(htmlSelect.name()).withOptions(options).build();
        return renderer.render();
    }

    private String renderInput(Field field){
        HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
        //TODO put this into another methods
        String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
        String localizedPlaceholder = htmlInput.localizedPlaceholder().equals("non-localized") ? null : htmlInput.localizedPlaceholder();
        HtmlInputRenderer inputRenderer = new HtmlInputRenderer.Builder(htmlInput.type())
                .withName(name)
                .withPlaceholder(htmlInput.placeholder())
                .withLocalizedPlaceholder(localizedPlaceholder)
                .build();
        HttpServletRequest req  = (HttpServletRequest)pageContext.getRequest();
        inputRenderer.setLocale(LocaleUtils.getLocaleFromCookies(req.getCookies()));
        return inputRenderer.construct();
    }

    private void write(String content){
        try {
            out.println(content);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private Class<?> classForName(String classPath){
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            write("<h1 style=\"color:red\">Form Class not found</h1>");
            return null;
        }
    }
}
