package org.thiki.kanban.foundation.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thiki.kanban.foundation.exception.UnauthorisedException;

import java.util.concurrent.TimeUnit;

/**
 * Created by xubitao on 04/26/16.
 */
@Configuration
@PropertySources(value = {@PropertySource("kanban.properties")})
public class ApplicationContextConfig implements ApplicationContextAware {
    @Value("${http.port}")
    private int port;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ((AnnotationConfigEmbeddedWebApplicationContext) applicationContext).scan("org.thiki", "cn.xubitao");
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port);
        factory.setSessionTimeout(50, TimeUnit.MINUTES);
        return factory;
    }

    @Bean
    public WebMvcConfigurer coreConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8008")
                        .allowedHeaders("token", "userName", "X-token")
                        .exposedHeaders("token", "userName", "X-token")
                        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18N/message");
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
        validatorFactory.setValidationMessageSource(messageSource());
        return validatorFactory;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(UnauthorisedException.class, "/error/businessException"));
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/businessException"));
            container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/invalidParamsException"));
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
            container.addErrorPages(new ErrorPage(Exception.class, "/error/500"));
        };
    }
}
