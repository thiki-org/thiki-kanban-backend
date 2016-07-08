package org.thiki.kanban.foundation.common;

/**
 * Created by winie on 7/4/2016.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 */
@Configuration
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     *
     * @throws Exception
     */
    public static ApplicationContext getApplicationContext() throws Exception {
        if (checkApplicationContext()) {
            throw new Exception("spring is not fonud!");
        }
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws Exception {
        if (checkApplicationContext()) {
            throw new Exception("spring is not fonud!");
        }
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @throws Exception
     */
    public static <T> T getBean(Class<T> clazz) throws Exception {
        if (checkApplicationContext()) {
            throw new Exception("spring is not fonud!");
        }
        return (T) applicationContext.getBean(clazz);
    }

    private static boolean checkApplicationContext() {
        return applicationContext == null;
    }
}
