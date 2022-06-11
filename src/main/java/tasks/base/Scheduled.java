package tasks.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scheduled {

    long initialDelay() default 0;

    long period();

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
