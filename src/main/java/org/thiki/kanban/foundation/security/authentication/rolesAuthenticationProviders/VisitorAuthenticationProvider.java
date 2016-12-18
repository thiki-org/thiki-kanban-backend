package org.thiki.kanban.foundation.security.authentication.rolesAuthenticationProviders;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.security.authentication.Authentication;
import org.thiki.kanban.foundation.security.authentication.AuthenticationResult;

import java.util.Map;

/**
 * Created by xubt on 24/10/2016.
 */
@Service("visitorAuthenticationProvider")
public class VisitorAuthenticationProvider implements Authentication {

    @Override
    public AuthenticationResult authenticate(Map pathValues, String userName) {
        return new AuthenticationResult();
    }
}
