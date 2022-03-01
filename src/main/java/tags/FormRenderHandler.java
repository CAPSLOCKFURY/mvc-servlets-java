package tags;

import forms.base.Form;
import forms.base.HtmlInput;
import forms.base.HtmlInputRenderer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import utils.LocaleUtils;

import java.io.IOException;
import java.util.Arrays;

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
                .filter(field -> field.isAnnotationPresent(HtmlInput.class))
                .forEach(field -> {
                    HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
                    //TODO put this into another methods
                    String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
                    String localizedPlaceholder = htmlInput.localizedPlaceholder().equals("non-localized") ? null : htmlInput.localizedPlaceholder();
                    //TODO probably add builder pattern here
                    HtmlInputRenderer inputRenderer = new HtmlInputRenderer(htmlInput.type(), name, htmlInput.placeholder(), localizedPlaceholder);
                    inputRenderer.setLocale(LocaleUtils.getLocaleFromCookies(((HttpServletRequest)pageContext.getRequest()).getCookies()));
                    write(inputRenderer.construct());
                });
        return SKIP_BODY;
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
