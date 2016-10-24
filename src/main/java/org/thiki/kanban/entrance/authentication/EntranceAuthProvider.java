package org.thiki.kanban.entrance.authentication;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.security.authentication.AuthProvider;

/**
 * Created by xubt on 10/24/2016.
 */
@Service("entranceAuthProvider")
public class EntranceAuthProvider extends AuthProvider {
    @Override
    public String getPathTemplate() {
        return "/entrance";
    }

    @Override
    public boolean authGet() {
        return true;
    }
}
