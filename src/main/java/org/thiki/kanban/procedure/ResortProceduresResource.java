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
public class ResortProceduresResource extends RestResource {

    @Resource
    private ProcedureResource procedureResourceService;

    @Resource
    private TLink tlink;

    public Object toResource(List<Procedure> procedureList, String boardId, String viewType, String userName) throws Exception {
        ResortProceduresResource resortProceduresResource = new ResortProceduresResource();
        List<Object> procedureResources = new ArrayList<>();
        for (Procedure procedure : procedureList) {
            Object procedureResource = procedureResourceService.toResource(procedure, boardId, userName);
            procedureResources.add(procedureResource);
        }

        resortProceduresResource.buildDataObject("procedures", procedureResources);
        Link proceduresLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId, viewType, userName)).withRel("procedures");
        resortProceduresResource.add(tlink.from(proceduresLink).build(userName));

        Link selfLink = linkTo(methodOn(ProceduresController.class).resort(procedureList, boardId, userName)).withSelfRel();
        resortProceduresResource.add(tlink.from(selfLink).build(userName));
        return resortProceduresResource.getResource();
    }
}
