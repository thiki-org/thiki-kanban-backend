package org.thiki.kanban.foundation.security.authentication;

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
    private List<Role> roles = new ArrayList<>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
