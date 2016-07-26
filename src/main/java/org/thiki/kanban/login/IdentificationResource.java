package org.thiki.kanban.login;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/8/16.
 */
public class IdentificationResource extends RestResource {
    public IdentificationResource(Identification identification) {
        super.domainObject = identification;
        Link loginLink = ControllerLinkBuilder.linkTo(methodOn(BoardsController.class).findByUserId(identification.getName())).withRel("boards");
        this.add(loginLink);
    }
}
