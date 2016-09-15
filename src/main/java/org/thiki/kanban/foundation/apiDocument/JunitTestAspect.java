package org.thiki.kanban.foundation.apiDocument;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.annotations.Theme;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;

@Aspect
@Component
public class JunitTestAspect {

    @Before("@annotation(org.junit.Test)")
    public void validateQueryParams(JoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Scenario scenario = method.getDeclaredAnnotation(Scenario.class);
        APIDocument.newRequest();
        APIDocument.scenario = scenario == null ? "场景未定义" : scenario.value();
        APIDocument.testCaseName = method.getName();

        Theme theme = joinPoint.getTarget().getClass().getAnnotation(Theme.class);
        APIDocument.theme = theme == null ? "未定义" : theme.value();
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
