package org.thiki.kanban.foundation.security.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubt on 17/12/2016.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(locations = {"classpath:roles-resources-mapping.yml"})
@Service
public class RolesResources {
    @Value("${server.contextPath}")
    protected String contextPath;
    private List<Role> roles = new ArrayList<>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public MatchResult match(String url, String method) {
        MatchResult matchResult = new MatchResult();
        for (Role role : roles) {
            List<ResourceTemplate> resourceTemplates = role.getResources();
            for (ResourceTemplate resourceTemplate : resourceTemplates) {
                if (resourceTemplate.match(url, method, contextPath)) {
                    matchResult.setRoleName(role.getName());
                    matchResult.setPathValues(resourceTemplate.getPathValues(url));
                }
            }
        }
        return matchResult;
    }
}
