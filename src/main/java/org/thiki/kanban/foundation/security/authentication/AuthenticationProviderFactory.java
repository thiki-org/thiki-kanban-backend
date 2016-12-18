package org.thiki.kanban.foundation.security.authentication;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.configuration.ApplicationContextProvider;

import java.util.Map;

/**
 * Created by xubt on 18/12/2016.
 */
@Service
public class AuthenticationProviderFactory {
    public Authentication loadProviderByRole(String roleName) {
        Map<String, Authentication> authProviders = ApplicationContextProvider.getApplicationContext().getBeansOfType(Authentication.class);
        for (Map.Entry entry : authProviders.entrySet()) {
            if (entry.getKey().equals(roleName + "AuthenticationProvider")) {
                return (Authentication) entry.getValue();
            }
        }
        return null;
    }
}
