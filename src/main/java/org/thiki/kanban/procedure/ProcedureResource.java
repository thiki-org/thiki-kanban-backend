package org.thiki.kanban.procedure;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
@Service
public class ProcedureResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(Procedure procedure, String boardId, String userName) throws Exception {
        ProcedureResource procedureResource = new ProcedureResource();
        procedureResource.domainObject = procedure;
        if (procedure != null) {
            Link selfLink = linkTo(methodOn(ProceduresController.class).findById(procedure.getId(), boardId, userName)).withSelfRel();
            procedureResource.add(tlink.from(selfLink).build(userName));

            Link cardsLink = linkTo(methodOn(CardsController.class).create(null, null, boardId, procedure.getId())).withRel("cards");
            procedureResource.add(tlink.from(cardsLink).build(userName));
        }
        Link allLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId, userName)).withRel("all");
        procedureResource.add(tlink.from(allLink).build(userName));
        return procedureResource.getResource();

    }

    public Object toResource(String boardId, String userName) throws Exception {
        ProcedureResource procedureResource = new ProcedureResource();
        Link allLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId, userName)).withRel("all");
        procedureResource.add(tlink.from(allLink).build(userName));
        return procedureResource.getResource();
    }
}
