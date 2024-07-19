package com.assessment.bank.serverapp.validators;

import com.assessment.bank.serverapp.annotations.Unique;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String fieldName;
    private Class<?> domainClass;

    private static Logger logger = LogManager.getLogger(UniqueValidator.class);

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.domainClass = constraintAnnotation.domainClass();
    }

    @Override
    @Transactional
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String query = "SELECT COUNT(e) FROM " + domainClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";
        logger.info("query: " + query);
        Long count = (Long) entityManager.createQuery(query)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0;
    }
}
