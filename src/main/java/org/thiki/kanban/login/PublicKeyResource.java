package org.thiki.kanban.login;

import org.thiki.kanban.foundation.common.RestResource;

/**
 * Created by xubt on 7/5/16.
 */
public class PublicKeyResource extends RestResource {
    public PublicKeyResource(PublicKey publicPublicKey) {
        this.domainObject = publicPublicKey;
    }
}
