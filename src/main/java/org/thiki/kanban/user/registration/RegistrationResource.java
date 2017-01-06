package org.thiki.kanban.user.registration;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.login.LoginController;
import org.thiki.kanban.user.User;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by mac on 6/22/16.
 */
@Service
public class RegistrationResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(User registeredUser) throws Exception {
        RegistrationResource registrationResource = new RegistrationResource();
        registrationResource.domainObject = registeredUser;
        if (registeredUser != null) {
            Link loginLink = linkTo(methodOn(LoginController.class).login(null, null)).withRel("login");
            registrationResource.add(tlink.from(loginLink).build());
        }
        return registrationResource.getResource();
    }
}
