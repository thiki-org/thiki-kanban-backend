package org.thiki.kanban.foundation.security;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thiki.kanban.foundation.security.filter.SecurityFilter;
import org.thiki.kanban.foundation.security.filter.SecurityFreeFilter;

import javax.annotation.Resource;

/**
 * Created by xubt on 7/10/16.
 */
@Configuration
public class SecurityConfiguration {
    @Resource
    private SecurityFilter securityFilter;

    @Bean
    public FilterRegistrationBean securityFilterRegistration() throws Exception {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(securityFilter);
        registration.addUrlPatterns("/*");
        registration.setName("securityFilter");
        registration.setOrder(2);
        registration.setMatchAfter(false);
        return registration;
    }
}
