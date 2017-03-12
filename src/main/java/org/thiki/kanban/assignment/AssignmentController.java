package org.thiki.kanban.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 6/16/16.
 */
@RestController
public class AssignmentController {
    private static Logger logger = LoggerFactory.getLogger(AssignmentController.class);
    @Autowired
    private AssignmentService assignmentService;

    @Resource
    private AssignmentsResource assignmentsResource;

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/assignments", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody List<Assignment> assignments, @PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        List<Assignment> savedAssignments = assignmentService.assign(assignments, cardId, boardId, userName);
        return Response.build(assignmentsResource.toResource(savedAssignments, boardId, stageId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/stages/{stageId}/cards/{cardId}/assignments", method = RequestMethod.GET)
    public HttpEntity findByCardId(@PathVariable String boardId, @PathVariable String stageId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        logger.info("Loading assignments by board [{}]", boardId);
        List<Assignment> assignmentList = assignmentService.findByCardId(cardId);
        return Response.build(assignmentsResource.toResource(assignmentList, boardId, stageId, cardId, userName));
    }
}
