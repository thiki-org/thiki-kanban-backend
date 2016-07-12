package org.thiki.kanban.registration;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.login.LoginController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by mac on 6/22/16.
 */
public class RegistrationResource extends RestResource {
    public RegistrationResource(Registration registration) throws Exception {
        super.domainObject = registration;
        if (registration != null) {
            Link loginLink = linkTo(methodOn(LoginController.class).login(registration.getName(), "yourPassWord")).withRel("login");
            this.add(loginLink);
        }
    }
}
