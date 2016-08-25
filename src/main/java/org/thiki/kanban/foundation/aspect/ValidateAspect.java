package org.thiki.kanban.foundation.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.thiki.kanban.foundation.exception.InvalidParamsException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

@Aspect
@Component
public class ValidateAspect {

    @Before("execution(* *(@org.springframework.web.bind.annotation.RequestBody (*),..))")
    public void validateBodyParams(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null && !(arg instanceof String) && !(arg instanceof Integer)) {
                Set<ConstraintViolation<Object>> constraintViolations = getValidator().validate(arg);
                if (constraintViolations.size() > 0) {
                    throw new InvalidParamsException(constraintViolations.iterator().next().getMessage());
                }
            }
        }
    }
    @Before("execution(* *(@org.springframework.web.bind.annotation.RequestBody (*),..,..))")
    public void validateBodyParams2(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null && !(arg instanceof String) && !(arg instanceof Integer)) {
                Set<ConstraintViolation<Object>> constraintViolations = getValidator().validate(arg);
                if (constraintViolations.size() > 0) {
                    throw new InvalidParamsException(constraintViolations.iterator().next().getMessage());
                }
            }
        }
    }

    @Before("@annotation(org.thiki.kanban.foundation.aspect.ValidateParams)")
    public void validateQueryParams(JoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] parameterValues = joinPoint.getArgs();

        Set<ConstraintViolation<Object>> violations = getMethodValidator().validateParameters(joinPoint.getTarget(), method, parameterValues);
        if (!violations.isEmpty()) {
            throw new InvalidParamsException(violations.iterator().next().getMessage());
        }
    }


    private ExecutableValidator getMethodValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator().forExecutables();
    }

    private Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
