package web.base.security;

import web.base.security.annotations.AuthenticatedOnly;
import web.base.security.annotations.ManagerOnly;
import web.base.security.annotations.NonAuthenticatedOnly;

import java.lang.annotation.Annotation;

public class SecurityStrategyFactory {

    public static Security getStrategy(Annotation annotation){
        if(annotation.annotationType().equals(AuthenticatedOnly.class)){
            return new web.base.security.AuthenticatedOnly();
        } else if (annotation.annotationType().equals(ManagerOnly.class)){
            return new web.base.security.ManagerOnly();
        } else if (annotation.annotationType().equals(NonAuthenticatedOnly.class)){
            return new web.base.security.NonAuthenticatedOnly();
        }
        throw new IllegalArgumentException("No security strategy found for annotation: " + annotation.annotationType().getSimpleName());
    }

    private SecurityStrategyFactory(){

    }

}
