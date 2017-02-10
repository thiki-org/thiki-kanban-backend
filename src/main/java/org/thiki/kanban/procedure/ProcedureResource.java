package org.thiki.kanban.procedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
    public static Logger logger = LoggerFactory.getLogger(ProcedureResource.class);
    @Resource
    private TLink tlink;

    @Cacheable(value = "procedure", key = "#userName+'procedure'+#boardId+#procedure.id")
    public Object toResource(Procedure procedure, String boardId, String userName) throws Exception {
        logger.info("build procedure resource.board:{},userName:{}", boardId, userName);
        ProcedureResource procedureResource = new ProcedureResource();
        procedureResource.domainObject = procedure;
        if (procedure != null) {
            Link selfLink = linkTo(methodOn(ProceduresController.class).findById(procedure.getId(), boardId, userName)).withSelfRel();
            procedureResource.add(tlink.from(selfLink).build(userName));

            Link cardsLink = linkTo(methodOn(CardsController.class).create(null, null, boardId, procedure.getId())).withRel("cards");
            procedureResource.add(tlink.from(cardsLink).build(userName));
        }
        Link allLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId, null, userName)).withRel("all");
        procedureResource.add(tlink.from(allLink).build(userName));
        logger.info("procedure resource building completed.board:{},userName:{}", boardId, userName);
        return procedureResource.getResource();
    }

    @Cacheable(value = "procedure", key = "#userName+'procedure'+#boardId")
    public Object toResource(String boardId, String userName) throws Exception {
        logger.info("build procedure resource.board:{},userName:{}", boardId, userName);
        ProcedureResource procedureResource = new ProcedureResource();
        Link allLink = linkTo(methodOn(ProceduresController.class).loadAll(boardId, null, userName)).withRel("all");
        procedureResource.add(tlink.from(allLink).build(userName));
        logger.info("procedure resource building completed.board:{},userName:{}", boardId, userName);
        return procedureResource.getResource();
    }
}
