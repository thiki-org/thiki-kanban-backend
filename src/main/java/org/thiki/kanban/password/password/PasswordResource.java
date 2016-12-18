package org.thiki.kanban.password.password;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.login.LoginController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 8/15/16.
 */
@Service
public class PasswordResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource() throws Exception {
        PasswordResource passwordResource = new PasswordResource();
        Link loginLink = linkTo(methodOn(LoginController.class).login(null, null)).withRel("login");
        passwordResource.add(tlink.from(loginLink).build());
        return passwordResource.getResource();
    }
}
