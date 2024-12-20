package ma.youcode.surveyit.annotation.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ma.youcode.surveyit.annotation.interfaces.Unique;

public class UniqueImp implements ConstraintValidator<Unique, String> {

    @PersistenceContext
    private EntityManager manager;

    private Class<?> entityClass;
    private String field;
    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (field == null) {
            return false;
        }

        String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE LOWER(e." + field + ") = LOWER(:value)";

        TypedQuery<Long> query = manager.createQuery(jpql , Long.class);
        query.setParameter("value" , value);

        Long count = query.getSingleResult();

        return count == 0;
    }
}