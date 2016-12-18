package org.thiki.kanban.foundation.security.authentication;

import java.util.Map;

/**
 * Created by xubt on 21/10/2016.
 */
public interface Authentication {
    AuthenticationResult authenticate(Map pathValues, String userName);
}
