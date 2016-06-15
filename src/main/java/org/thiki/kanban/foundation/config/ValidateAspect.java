package org.thiki.kanban.foundation.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.InvalidParameterException;
import java.util.Set;

/**
 * Created by xubt on 6/15/16.
 */
@Aspect
@Component
public class ValidateAspect {
    private static Validator validator;

    @Before("execution(* *(@org.springframework.web.bind.annotation.RequestBody (*),..))")
    public void validate(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (!(arg instanceof String) && !(arg instanceof Integer)) {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                validator = factory.getValidator();
                Set<ConstraintViolation<Object>> constraintViolations = validator.validate(arg);
                if (constraintViolations.size() > 0) {
                    throw new InvalidParameterException(constraintViolations.iterator().next().getMessage());
                }
            }
        }
    }
}
