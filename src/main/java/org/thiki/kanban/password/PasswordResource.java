package org.thiki.kanban.password;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.login.LoginController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 8/15/16.
 */
public class PasswordResource extends RestResource {
    public PasswordResource() throws Exception {
        Link loginLink = linkTo(methodOn(LoginController.class).login(null, null)).withRel("login");
        this.add(loginLink);
    }
}
