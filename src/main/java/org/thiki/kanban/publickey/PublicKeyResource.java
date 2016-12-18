package org.thiki.kanban.publickey;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.login.LoginController;
import org.thiki.kanban.user.registration.RegistrationController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class PublicKeyResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(PublicKey publicPublicKey) throws Exception {
        PublicKeyResource publicKeyResource = new PublicKeyResource();
        publicKeyResource.domainObject = publicPublicKey;
        Link loginLink = linkTo(methodOn(LoginController.class).login(null, null)).withRel("login");
        publicKeyResource.add(tlink.from(loginLink).build());

        Link registrationLink = linkTo(methodOn(RegistrationController.class).registerNewUser(null)).withRel("registration");
        publicKeyResource.add(tlink.from(registrationLink).build());
        return publicKeyResource.getResource();
    }
}
