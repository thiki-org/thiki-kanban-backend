package org.thiki.kanban.publickey.authentication;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.security.authentication.AuthenticationProvider;

/**
 * Created by xubt on 10/24/2016.
 */
@Service("publicKeyAuthProvider")
public class PublicKeyAuthenticationProvider extends AuthenticationProvider {
    @Override
    public String getPathTemplate() {
        return "/publicKey";
    }

    @Override
    public boolean authGet() {
        return true;
    }
}
