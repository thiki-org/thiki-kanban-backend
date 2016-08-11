package org.thiki.kanban.foundation.aspect;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;
@Component
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateParams {

}
