package forms.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HtmlTextArea {
    //TODO add placeholders
    String id() default "";

    String name();

    String rows();

    String cols();

    HtmlLabel label() default @HtmlLabel(forElement = "", text = "");

}