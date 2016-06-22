package org.thiki.kanban.registration;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.user.UserProfile;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by mac on 6/22/16.
 */
public class RegistrationResource extends RestResource {
    public RegistrationResource(Map<String, Object> result) {
        super.domainObject = result;
    }

}
