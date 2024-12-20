package ma.youcode.surveyit.annotation.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ma.youcode.surveyit.annotation.interfaces.Exists;

public class ExistsImp implements ConstraintValidator<Exists, Long> {

    @PersistenceContext
    private EntityManager manager;

    private Class<?> entityClass;

    @Override
    public void initialize(Exists constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {

        if (id == null) {
            return false;
        }

        boolean exists = manager.find(entityClass, id) != null;

        if(!exists){
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("id")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }


}
