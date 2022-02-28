package tags;

import forms.base.HtmlInput;
import forms.base.HtmlInputRenderer;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.lang.reflect.Field;

public class FormRenderHandler extends TagSupport {

    public String formClassPath;

    public void setFormClassPath(String formClassPath){
        this.formClassPath = formClassPath;
    }

    @Override
    public int doStartTag(){
        JspWriter out = pageContext.getOut();
        try {
            Class<?> formClass = Class.forName(formClassPath);
            for(Field field : formClass.getDeclaredFields()){
                if(field.isAnnotationPresent(HtmlInput.class)){
                    HtmlInput htmlInput = field.getDeclaredAnnotation(HtmlInput.class);
                    String name = htmlInput.name().equals("") ? field.getName() : htmlInput.name();
                    HtmlInputRenderer inputRenderer = new HtmlInputRenderer(htmlInput.type(), name, htmlInput.placeholder());
                    write(out, inputRenderer.construct());
                }
            }
        } catch (ClassNotFoundException e) {
            write(out,"<h1 style=\"color:red\">Form Class not found</h1>");
        }
        return SKIP_BODY;
    }

    private void write(JspWriter out, String content){
        try {
            out.println(content);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
