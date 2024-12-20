package ma.youcode.surveyit.annotation.interfaces;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ma.youcode.surveyit.annotation.implementations.UniqueImp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueImp.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD , ElementType.FIELD , ElementType.PARAMETER})

public @interface Unique {

    String message() default "this value must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entity();
    String field();
}
