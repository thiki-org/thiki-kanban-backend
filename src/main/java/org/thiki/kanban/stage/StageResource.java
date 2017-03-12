package org.thiki.kanban.stage;

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
public class StageResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(StageResource.class);
    @Resource
    private TLink tlink;

    @Cacheable(value = "stage", key = "#userName+'stage'+#boardId+#stage.id")
    public Object toResource(Stage stage, String boardId, String userName) throws Exception {
        logger.info("build stage resource.board:{},userName:{}", boardId, userName);
        StageResource stageResource = new StageResource();
        stageResource.domainObject = stage;
        if (stage != null) {
            Link selfLink = linkTo(methodOn(StagesController.class).findById(stage.getId(), boardId, userName)).withSelfRel();
            stageResource.add(tlink.from(selfLink).build(userName));

            Link cardsLink = linkTo(methodOn(CardsController.class).create(null, null, boardId)).withRel("cards");
            stageResource.add(tlink.from(cardsLink).build(userName));
        }
        Link allLink = linkTo(methodOn(StagesController.class).loadAll(boardId, null, userName)).withRel("all");
        stageResource.add(tlink.from(allLink).build(userName));
        logger.info("stage resource building completed.board:{},userName:{}", boardId, userName);
        return stageResource.getResource();
    }

    @Cacheable(value = "stage", key = "#userName+'stage'+#boardId")
    public Object toResource(String boardId, String userName) throws Exception {
        logger.info("build stage resource.board:{},userName:{}", boardId, userName);
        StageResource stageResource = new StageResource();
        Link allLink = linkTo(methodOn(StagesController.class).loadAll(boardId, null, userName)).withRel("all");
        stageResource.add(tlink.from(allLink).build(userName));
        logger.info("stage resource building completed.board:{},userName:{}", boardId, userName);
        return stageResource.getResource();
    }
}
