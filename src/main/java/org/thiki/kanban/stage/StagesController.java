package org.thiki.kanban.stage;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
public class StagesController {
    @Resource
    private StagesService stagesService;

    @Resource
    private StageResource stageResource;
    @Resource
    private StagesResource stagesResource;
    @Resource
    private ResortStagesResource resortStagesResource;

    @RequestMapping(value = "/boards/{boardId}/stages", method = RequestMethod.GET)
    public HttpEntity loadAll(@PathVariable String boardId, @RequestParam(required = false) String viewType, @RequestHeader String userName) throws Exception {
        List<Stage> stageList = stagesService.loadByBoardId(boardId, viewType);
        return Response.build(stagesResource.toResource(stageList, boardId, viewType, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        Stage stage = stagesService.findById(id);
        return Response.build(stageResource.toResource(stage, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}", method = RequestMethod.PUT)
    public HttpEntity<StageResource> update(@RequestBody Stage stage, @PathVariable String stageId, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        Stage updatedStage = stagesService.modifyStage(stageId, stage, boardId);

        return Response.build(stageResource.toResource(updatedStage, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String id, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        stagesService.deleteById(id);
        return Response.build(stageResource.toResource(boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Stage stage, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        Stage savedStage = stagesService.create(userName, boardId, stage);

        return Response.post(stageResource.toResource(savedStage, boardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/movement", method = RequestMethod.PUT)
    public HttpEntity resort(@RequestBody List<Stage> stages, @PathVariable String boardId, @RequestHeader String userName) throws Exception {
        List<Stage> stageList = stagesService.resortStages(stages, boardId);
        return Response.build(resortStagesResource.toResource(stageList, boardId, null, userName));
    }
}
