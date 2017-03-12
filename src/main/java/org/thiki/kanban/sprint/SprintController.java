package org.thiki.kanban.sprint;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubt on 04/02/2017.
 */
@RestController
public class SprintController {
    @Resource
    private SprintService sprintService;

    @Resource
    private SprintResource sprintResource;

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/sprints/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @PathVariable String boardId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        return null;
    }

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/sprints", method = RequestMethod.POST)
    public HttpEntity createSprint(@RequestBody Sprint sprint, @PathVariable String boardId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        Sprint savedSprint = sprintService.createSprint(sprint, boardId, userName);

        return Response.post(sprintResource.toResource(savedSprint, boardId, projectId, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/sprints/{sprintId}", method = RequestMethod.PUT)
    public HttpEntity updateSprint(@RequestBody Sprint sprint, @PathVariable String sprintId, @PathVariable String boardId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        Sprint savedSprint = sprintService.updateSprint(sprintId, sprint, boardId, userName);

        return Response.build(sprintResource.toResource(savedSprint, boardId, projectId, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/boards/{boardId}/sprints/activeSprint", method = RequestMethod.GET)
    public HttpEntity loadActiveSprint(@PathVariable String boardId, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        Sprint activeSprint = sprintService.loadActiveSprint(boardId, userName);

        return Response.build(sprintResource.toResource(activeSprint, boardId, projectId, userName));
    }
}
