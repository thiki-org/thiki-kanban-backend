package org.thiki.kanban.password.passwordRetrieval;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.password.PasswordController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class PasswordRetrievalResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(String userName) throws Exception {
        PasswordRetrievalResource passwordRetrievalResource = new PasswordRetrievalResource();
        Link passwordRetrievalLink = ControllerLinkBuilder.linkTo(methodOn(PasswordController.class).passwordRetrieval(null, userName)).withRel("passwordResetApplication");
        passwordRetrievalResource.add(tlink.from(passwordRetrievalLink).build(userName));
        return passwordRetrievalResource.getResource();
    }
}
