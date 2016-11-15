package org.thiki.kanban.procedure;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class ResortProceduresResource extends RestResource {

    public ResortProceduresResource(List<Procedure> procedureList, String boardId) throws Exception {

        List<ProcedureResource> procedureResources = new ArrayList<>();
        for (Procedure procedure : procedureList) {
            ProcedureResource procedureResource = new ProcedureResource(procedure, boardId);
            procedureResources.add(procedureResource);
        }

        this.buildDataObject("procedures", procedureResources);
        Link proceduresLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId)).withRel("procedures");
        this.add(proceduresLink);

        Link selfLink = linkTo(methodOn(ProceduresController.class).resort(procedureList, boardId)).withSelfRel();
        this.add(selfLink);
    }
}
