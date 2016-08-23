package org.thiki.kanban.foundation.redirection;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.login.LoginController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 05/26/16.
 */
public class RedirectionResource extends RestResource {
    public RedirectionResource(Error error) throws Exception {
        this.domainObject = error;
        if (error != null) {
            Link identificationLink = linkTo(methodOn(LoginController.class).login(null, null)).withRel("identification");
            this.add(identificationLink);
        }
    }
}
