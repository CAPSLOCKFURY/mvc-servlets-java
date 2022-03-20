package forms.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HtmlSelect {
    String name();

    String id() default "";

    String literal() default "";

    HtmlOption[] options() default {};

    /**
     * If this is not default, html options will be loaded from request attribute with given name
     */
    String dynamicOptionsAttribute() default "";

    HtmlLabel label() default @HtmlLabel(forElement = "", text = "");

}
