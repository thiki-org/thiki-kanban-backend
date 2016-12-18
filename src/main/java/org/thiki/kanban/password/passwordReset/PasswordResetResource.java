package org.thiki.kanban.password.passwordReset;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.password.PasswordController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 8/15/16.
 */
@Service
public class PasswordResetResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(String userName) throws Exception {
        PasswordResetResource passwordResetResource = new PasswordResetResource();
        Link passwordRetrievalLink = ControllerLinkBuilder.linkTo(methodOn(PasswordController.class).password(null, userName)).withRel("password");
        passwordResetResource.add(tlink.from(passwordRetrievalLink).build(userName));
        return passwordResetResource.getResource();
    }
}
