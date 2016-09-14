package org.thiki.kanban.registration;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.login.LoginController;
import org.thiki.kanban.user.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by mac on 6/22/16.
 */
public class RegistrationResource extends RestResource {
    public RegistrationResource(User registeredUser) throws Exception {
        super.domainObject = registeredUser;
        if (registeredUser != null) {
            Link loginLink = linkTo(methodOn(LoginController.class).login(null, null)).withRel("login");
            this.add(loginLink);
        }
    }
}
