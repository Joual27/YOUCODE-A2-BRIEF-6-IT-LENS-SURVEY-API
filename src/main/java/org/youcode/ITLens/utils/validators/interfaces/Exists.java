package org.youcode.ITLens.utils.validators.interfaces;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.youcode.ITLens.utils.validators.IdExistsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IdExistsValidator.class)
@Target({ElementType.PARAMETER , ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {
    Class<?> entityClass();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
