package org.thiki.kanban.procedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
    public static Logger logger = LoggerFactory.getLogger(ProceduresResource.class);
    @Resource
    private TLink tlink;
    @Resource
    private ProcedureResource procedureResourceService;

    @Cacheable(value = "procedure", key = "#userName+'procedures'+#boardId")
    public Object toResource(List<Procedure> procedureList, String boardId, String viewType, String userName) throws Exception {
        logger.info("build procedures resource.board:{},userName:{}", boardId, userName);
        ProceduresResource proceduresResource = new ProceduresResource();
        List<Object> procedureResources = new ArrayList<>();
        for (Procedure procedure : procedureList) {
            Object procedureResource = procedureResourceService.toResource(procedure, boardId, userName);
            procedureResources.add(procedureResource);
        }

        proceduresResource.buildDataObject("procedures", procedureResources);
        Link selfLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId, viewType, userName)).withSelfRel();
        proceduresResource.add(tlink.from(selfLink).build(userName));

        Link sortNumbersLink = linkTo(methodOn(ProceduresController.class).resort(procedureList, boardId, userName)).withRel("sortNumbers");
        proceduresResource.add(tlink.from(sortNumbersLink).build(userName));
        logger.info("procedures resource building completed.board:{},userName:{}", boardId, userName);
        return proceduresResource.getResource();
    }
}
