package tags;

import forms.base.*;
import forms.base.annotations.*;
import forms.base.renderers.HtmlInputRenderer;
import forms.base.renderers.HtmlLabelRenderer;
import forms.base.renderers.HtmlSelectRenderer;
import forms.base.renderers.HtmlTextAreaRenderer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import utils.LocaleUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
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
                .filter(field -> field.isAnnotationPresent(HtmlInput.class)
                        || field.isAnnotationPresent(HtmlSelect.class)
                        || field.isAnnotationPresent(HtmlTextArea.class))
                .forEach(field -> {
                    if(field.isAnnotationPresent(HtmlInput.class)) {
                        write(renderInput(field));
                        return;
                    }
                    if(field.isAnnotationPresent(HtmlSelect.class)){
                        write(renderSelect(field));
                        return;
                    }
                    if(field.isAnnotationPresent(HtmlTextArea.class)){
                        write(renderTextArea(field));
                    }
                });
        return SKIP_BODY;
    }

    private String renderSelect(Field field){
        HtmlSelect htmlSelect = field.getDeclaredAnnotation(HtmlSelect.class);
        renderLabel(htmlSelect.label());
        Map<String, String> options = Arrays.stream(htmlSelect.options())
                .collect(Collectors.toMap(HtmlOption::value, HtmlOption::name));
        HtmlSelectRenderer renderer = new HtmlSelectRenderer.Builder()
                .withName(htmlSelect.name()).withOptions(options).build();
        return renderer.render();
    }

    private String renderInput(Field field){
        HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
        HtmlLabel label = htmlInput.label();
        renderLabel(label);
        //TODO put this into another methods
        String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
        String localizedPlaceholder = htmlInput.localizedPlaceholder().equals("non-localized") ? null : htmlInput.localizedPlaceholder();
        HtmlInputRenderer inputRenderer = new HtmlInputRenderer.Builder(htmlInput.type())
                .withName(name)
                .withPlaceholder(htmlInput.placeholder())
                .withLocalizedPlaceholder(localizedPlaceholder)
                .build();
        HttpServletRequest req  = (HttpServletRequest)pageContext.getRequest();
        String localeName = LocaleUtils.getLocaleFromCookies(req.getCookies());
        inputRenderer.setLocale(new Locale(localeName));
        return inputRenderer.render();
    }

    private String renderTextArea(Field field){
        HtmlTextArea htmlTextArea = field.getDeclaredAnnotation(HtmlTextArea.class);
        renderLabel(htmlTextArea.label());
        HtmlTextAreaRenderer renderer = new HtmlTextAreaRenderer.Builder()
                .withName(htmlTextArea.name())
                .withRows(htmlTextArea.rows())
                .withCols(htmlTextArea.cols()).build();
        return renderer.render();
    }

    private void renderLabel(HtmlLabel label) {
        HttpServletRequest req  = (HttpServletRequest)pageContext.getRequest();
        String localeName = LocaleUtils.getLocaleFromCookies(req.getCookies());
        if(!label.forElement().equals("")){
            HtmlLabelRenderer labelRenderer = new HtmlLabelRenderer.Builder()
                    .withForElement(label.forElement())
                    .withText(label.text())
                    .withLocalizedText(label.localizedText())
                    .build();
            labelRenderer.setLocale(new Locale(localeName));
            write(labelRenderer.render());
        }
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
