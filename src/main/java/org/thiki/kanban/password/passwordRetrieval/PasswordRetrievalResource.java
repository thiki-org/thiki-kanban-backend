package org.thiki.kanban.password.passwordRetrieval;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.password.PasswordController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/5/16.
 */
public class PasswordRetrievalResource extends RestResource {
    public PasswordRetrievalResource() throws Exception {
        Link passwordRetrievalLink = ControllerLinkBuilder.linkTo(methodOn(PasswordController.class).passwordRetrieval(null)).withRel("passwordResetApplication");
        this.add(passwordRetrievalLink);
    }
}
