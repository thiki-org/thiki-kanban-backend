package org.thiki.kanban.password;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/5/16.
 */
public class PasswordRetrievalResource extends RestResource {
    public PasswordRetrievalResource() throws Exception {
        Link passwordRetrievalLink = linkTo(methodOn(PasswordRetrievalController.class).passwordRetrieval(null)).withRel("passwordResetApplication");
        this.add(passwordRetrievalLink);
    }
}
