package org.thiki.kanban.foundation.security;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thiki.kanban.foundation.security.filter.SecurityFilter;
import org.thiki.kanban.foundation.security.filter.SecurityFreeFilter;

/**
 * Created by xubt on 7/10/16.
 */
@Configuration
public class SecurityConfiguration {
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
