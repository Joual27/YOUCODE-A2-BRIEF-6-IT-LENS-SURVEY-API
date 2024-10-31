package org.youcode.ITLens.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.youcode.ITLens.common.exceptions.EntityNotFoundException;
import org.youcode.ITLens.utils.validators.interfaces.Exists;

import java.util.Optional;

@Component
public class IdExistsValidator implements ConstraintValidator<Exists , Long> {

    private final ApplicationContext applicationContext;
    private Class<?> entityClass;

    public IdExistsValidator (ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(Exists annotation){
        this.entityClass = annotation.entityClass();
    }

    @Override
    public boolean isValid(Long id , ConstraintValidatorContext context){
        if (id == null){
            return false;
        }
        String daoBean = entityClass.getSimpleName() + "PersistenceAdapter";
        Object dao = applicationContext.getBean(daoBean);
        try {
            var method = dao.getClass().getMethod("findById", Long.class);
            Optional<?> optionalEntity = (Optional<?>) method.invoke(dao, id);

            if (!optionalEntity.isPresent()) {
                throw new EntityNotFoundException("No record of type " + entityClass.getSimpleName() + " with ID " + id + " was found.");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
