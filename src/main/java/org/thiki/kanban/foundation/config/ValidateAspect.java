package org.thiki.kanban.foundation.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import java.security.InvalidParameterException;
import java.util.Set;

/**
 * Created by xubt on 6/15/16.
 */
@Aspect
@Component
public class ValidateAspect {
//    private static Validator validator;
    @Resource
    LocalValidatorFactoryBean validatorFactoryBean;
    @Resource
    MessageSource messageSource;


    @Before("execution(* *(@org.springframework.web.bind.annotation.RequestBody (*),..))")
    public void validate(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (!(arg instanceof String) && !(arg instanceof Integer)) {
//                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//                validatorFactoryBean = new LocalValidatorFactoryBean();
//                validatorFactoryBean.setValidationMessageSource(messageSource);
//                validator = factory.getValidator();
                Set<ConstraintViolation<Object>> constraintViolations = validatorFactoryBean.validate(arg);
                if (constraintViolations.size() > 0) {
                    throw new InvalidParameterException(constraintViolations.iterator().next().getMessage());
                }
            }
        }
    }


}
