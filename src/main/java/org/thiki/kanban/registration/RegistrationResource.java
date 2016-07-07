package org.thiki.kanban.registration;

import org.thiki.kanban.foundation.common.RestResource;

/**
 * Created by mac on 6/22/16.
 */
public class RegistrationResource extends RestResource {
    public RegistrationResource(Registration registration) {
        super.domainObject = registration;
        if (registration != null) {

        }
    }
}
