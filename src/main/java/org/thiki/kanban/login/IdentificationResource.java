package org.thiki.kanban.login;

import org.thiki.kanban.foundation.common.RestResource;

/**
 * Created by xubt on 7/8/16.
 */
public class IdentificationResource extends RestResource {
    public IdentificationResource(Identification identification) {
        super.domainObject = identification;
    }
}
