package org.thiki.kanban.stage;

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
public class ResortStagesResource extends RestResource {

    @Resource
    private StageResource stageResourceService;

    @Resource
    private TLink tlink;

    public Object toResource(List<Stage> stageList, String boardId, String viewType, String userName) throws Exception {
        ResortStagesResource resortStagesResource = new ResortStagesResource();
        List<Object> stageResources = new ArrayList<>();
        for (Stage stage : stageList) {
            Object stageResource = stageResourceService.toResource(stage, boardId, userName);
            stageResources.add(stageResource);
        }

        resortStagesResource.buildDataObject("stages", stageResources);
        Link stagesLink = linkTo(methodOn(StagesController.class).loadAll(boardId, viewType, userName)).withRel("stages");
        resortStagesResource.add(tlink.from(stagesLink).build(userName));

        Link selfLink = linkTo(methodOn(StagesController.class).resort(stageList, boardId, userName)).withSelfRel();
        resortStagesResource.add(tlink.from(selfLink).build(userName));
        return resortStagesResource.getResource();
    }
}
