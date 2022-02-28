package tags;

import forms.base.HtmlInput;
import forms.base.HtmlInputRenderer;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

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
        //TODO make sure class extends Form
        out = pageContext.getOut();
        Class<?> formClass = classForName(formClassPath);
        if(formClass == null){
            return SKIP_BODY;
        }
        Arrays.stream(formClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(HtmlInput.class))
                .forEach(field -> {
                    HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
                    String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
                    HtmlInputRenderer inputRenderer = new HtmlInputRenderer(htmlInput.type(), name, htmlInput.placeholder());
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
