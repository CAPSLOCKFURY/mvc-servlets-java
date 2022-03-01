package forms.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HtmlInput {
    InputType type();

    String name() default "";

    String placeholder() default "";

    String localizedPlaceholder() default "non-localized";
}
