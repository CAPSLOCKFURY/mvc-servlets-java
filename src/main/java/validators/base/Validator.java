package validators.base;

import java.lang.annotation.Annotation;

public interface Validator<A extends Annotation, V> {

    default void initialize(A annotation){}

    boolean validate(A annotation, V value);

}
