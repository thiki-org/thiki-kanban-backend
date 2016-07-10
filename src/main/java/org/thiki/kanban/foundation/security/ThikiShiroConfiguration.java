package org.thiki.kanban.foundation.security;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thiki.kanban.foundation.security.filter.NoAuthcFilter;
import org.thiki.kanban.foundation.security.filter.StatelessAuthcFilter;
import org.thiki.kanban.foundation.security.mgt.StatelessDefaultSubjectFactory;
import org.thiki.kanban.foundation.security.realm.StatelessRealm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xubt on 7/4/16.
 */
@Configuration
public class ThikiShiroConfiguration {
    //Realm实现
    @Bean
    public StatelessRealm initStatelessRealm() {
        StatelessRealm statelessRealm = new StatelessRealm();
        statelessRealm.setCachingEnabled(false);
        return statelessRealm;
    }

    //Subject工厂
    @Bean
    public StatelessDefaultSubjectFactory statelessDefaultSubjectFactory() {
        return new StatelessDefaultSubjectFactory();
    }

    //会话管理器
    @Bean
    public DefaultSessionManager defaultSessionManager() {
        DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
        defaultSessionManager.setSessionValidationSchedulerEnabled(false);
        return defaultSessionManager;
    }

    //安全管理器

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(initStatelessRealm());
        defaultWebSecurityManager.setSubjectFactory(statelessDefaultSubjectFactory());
        defaultWebSecurityManager.setSessionManager(defaultSessionManager());
        return defaultWebSecurityManager;
    }

    //相当于调用SecurityUtils.setSecurityManager(securityManager)
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(new DefaultWebSecurityManager[]{defaultWebSecurityManager()});
        return methodInvokingFactoryBean;
    }

    //Shiro的Web过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        shiroFilterFactoryBean.setFilters(new HashMap() {
            {
                put("statelessAuthc", new StatelessAuthcFilter());
                put("anon", new NoAuthcFilter());
            }
        });
        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();


        filterChainDefinitionMapping.put("/identification", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        return shiroFilterFactoryBean;
    }

    //Shiro生命周期处理器
    @Bean
    public LifecycleBeanPostProcessor initLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
