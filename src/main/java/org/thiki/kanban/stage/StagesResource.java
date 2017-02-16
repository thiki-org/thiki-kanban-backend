package org.thiki.kanban.stage;

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
public class StagesResource extends RestResource {
    public static Logger logger = LoggerFactory.getLogger(StagesResource.class);
    @Resource
    private TLink tlink;
    @Resource
    private StageResource stageResourceService;

    @Cacheable(value = "stage", key = "#userName+'stages'+#boardId+#viewType")
    public Object toResource(List<Stage> stageList, String boardId, String viewType, String userName) throws Exception {
        logger.info("build stages resource.board:{},viewType:{},userName:{}", boardId, viewType, userName);
        StagesResource stagesResource = new StagesResource();
        List<Object> stageResources = new ArrayList<>();
        for (Stage stage : stageList) {
            Object stageResource = stageResourceService.toResource(stage, boardId, userName);
            stageResources.add(stageResource);
        }

        stagesResource.buildDataObject("stages", stageResources);
        Link selfLink = linkTo(methodOn(StagesController.class).loadAll(boardId, viewType, userName)).withSelfRel();
        stagesResource.add(tlink.from(selfLink).build(userName));

        Link sortNumbersLink = linkTo(methodOn(StagesController.class).resort(stageList, boardId, userName)).withRel("sortNumbers");
        stagesResource.add(tlink.from(sortNumbersLink).build(userName));
        logger.info("stages resource building completed.board:{},userName:{}", boardId, userName);
        return stagesResource.getResource();
    }
}
