package org.youcode.ITLens.utils.validators;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.youcode.ITLens.utils.validators.interfaces.Exists;

@Component
public class IdExistsValidator implements ConstraintValidator<Exists, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(Exists annotation) {
        this.entityClass = annotation.entityClass();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("ID must not be null.")
                    .addConstraintViolation();
            return false;
        }

        try {
            Object entity = entityManager.find(entityClass, id);
            if (entity == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("No " + entityClass.getSimpleName() + " Was found with given ID!")
                        .addConstraintViolation();
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during validation: " + e.getMessage(), e);
        }
    }
}