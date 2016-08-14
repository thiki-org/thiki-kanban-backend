package org.thiki.kanban.publickey;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.login.LoginController;
import org.thiki.kanban.registration.RegistrationController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/5/16.
 */
public class PublicKeyResource extends RestResource {
    public PublicKeyResource(PublicKey publicPublicKey) throws Exception {
        this.domainObject = publicPublicKey;
        Link loginLink = linkTo(methodOn(LoginController.class).login(null, null)).withRel("login");
        this.add(loginLink);

        Link registrationLink = linkTo(methodOn(RegistrationController.class).registerNewUser(null)).withRel("registration");
        this.add(registrationLink);
    }
}
