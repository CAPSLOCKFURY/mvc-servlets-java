package forms.base.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlLabel {
    String forElement();

    String text() default "";

    String localizedText() default "non-localized";
}
