package org.thiki.kanban.foundation.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@Configuration
public class WebSpringConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(new ArrayList<MediaType>() {
            {
                this.add(MediaType.APPLICATION_JSON);
                this.add(MediaType.APPLICATION_JSON_UTF8);
            }
        });
        converters.add(converter);
    }

    @Bean
    public FilterRegistrationBean securityFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns("/resource");
        registration.setName("securityFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean securityFreeFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFreeFilter());
        registration.addUrlPatterns("/*");
        registration.setName("securityFreeFilter");
        registration.setOrder(1);
        return registration;
    }
}
