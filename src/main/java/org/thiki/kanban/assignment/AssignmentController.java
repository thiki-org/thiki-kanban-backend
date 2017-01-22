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
    private AssignmentResource assignmentResource;

    @Resource
    private AssignmentsResource assignmentsResource;

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/assignments", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Assignment assignment, @PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        Assignment savedAssignment = assignmentService.assign(assignment, cardId, userName);
        return Response.post(assignmentResource.toResource(savedAssignment, boardId, procedureId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/assignments/{assignmentId}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @PathVariable String assignmentId, @RequestHeader String userName) throws Exception {
        Assignment foundAssignment = assignmentService.findById(assignmentId);

        return Response.build(assignmentResource.toResource(foundAssignment, boardId, procedureId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/assignments", method = RequestMethod.GET)
    public HttpEntity findByCardId(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws Exception {
        logger.info("Loading assignments by board [{}]", boardId);
        List<Assignment> assignmentList = assignmentService.findByCardId(cardId);
        return Response.build(assignmentsResource.toResource(assignmentList, boardId, procedureId, cardId, userName));
    }

    @RequestMapping(value = "/boards/{boardId}/procedures/{procedureId}/cards/{cardId}/assignments/{assignmentId}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String boardId, @PathVariable String procedureId, @PathVariable String cardId, @PathVariable String assignmentId, @RequestHeader String userName) throws Exception {
        assignmentService.leaveCard(assignmentId, cardId, userName);
        return Response.build(assignmentResource.toResource(boardId, procedureId, cardId, userName));
    }
}
