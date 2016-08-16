package org.thiki.kanban.password.passwordReset;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.password.PasswordController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 8/15/16.
 */
public class PasswordResetResource extends RestResource {
    public PasswordResetResource() throws Exception {
        Link passwordRetrievalLink = ControllerLinkBuilder.linkTo(methodOn(PasswordController.class).password(null)).withRel("password");
        this.add(passwordRetrievalLink);
    }
}
