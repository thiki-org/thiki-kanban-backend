package org.thiki.kanban.procedure;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */

@Service
public class ProceduresResource extends RestResource {
    @Resource
    private TLink tlink;
    @Resource
    private ProcedureResource procedureResourceService;

    public Object toResource(List<Procedure> procedureList, String boardId, String userName) throws Exception {
        ProceduresResource proceduresResource = new ProceduresResource();
        List<Object> procedureResources = new ArrayList<>();
        for (Procedure procedure : procedureList) {
            Object procedureResource = procedureResourceService.toResource(procedure, boardId, userName);
            procedureResources.add(procedureResource);
        }

        proceduresResource.buildDataObject("procedures", procedureResources);
        Link selfLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId, userName)).withSelfRel();
        proceduresResource.add(tlink.from(selfLink).build(userName));

        Link sortNumbersLink = linkTo(methodOn(ProceduresController.class).resort(procedureList, boardId, userName)).withRel("sortNumbers");
        proceduresResource.add(tlink.from(sortNumbersLink).build(userName));
        return proceduresResource.getResource();
    }
}
