package org.thiki.kanban.foundation.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by xubitao on 04/26/16.
 */
@Configuration
@PropertySources(value = {@PropertySource("kanban.properties"), @PropertySource("log4j.properties")})
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
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }
}
