package org.thiki.kanban.password;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 8/15/16.
 */
public class PasswordResetResource extends RestResource {
    public PasswordResetResource() throws Exception {
        Link passwordRetrievalLink = linkTo(methodOn(PasswordRetrievalController.class).password(null)).withRel("password");
        this.add(passwordRetrievalLink);
    }
}
