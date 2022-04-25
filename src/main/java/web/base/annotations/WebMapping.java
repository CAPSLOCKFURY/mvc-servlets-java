package web.base.annotations;

import web.base.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All commands with this annotation or methods in web controller in commands.impl/controllers package will be loaded into the command registry at start up of application
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface WebMapping {
    String url();
    RequestMethod method();
}
