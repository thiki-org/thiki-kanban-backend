package org.thiki.kanban.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

/**
 * Created by xubitao on 6/16/16.
 */
@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(value = "/entries/{entryId}/cards/{cardId}/assignments", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Assignment assignment, @PathVariable String entryId, @PathVariable String cardId, @RequestHeader String userId) {
        Assignment savedAssignment = assignmentService.create(assignment, cardId, userId);
        return Response.post(new AssignmentResource(savedAssignment, entryId, cardId));
    }

    @RequestMapping(value = "/entries/{entryId}/cards/{cardId}/assignments/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String entryId, @PathVariable String cardId, @PathVariable String id) {
        Assignment foundAssignment = assignmentService.findById(id);

        return Response.build(new AssignmentResource(foundAssignment, entryId, cardId));
    }

    @RequestMapping(value = "/entries/{entryId}/cards/{cardId}/assignments", method = RequestMethod.GET)
    public HttpEntity findByCardId(@PathVariable String entryId, @PathVariable String cardId) {
        List<Assignment> assignmentList = assignmentService.findByCardId(cardId);
        return Response.build(new AssignmentsResource(assignmentList, entryId, cardId));
    }

    @RequestMapping(value = "/entries/{entryId}/cards/{cardId}/assignments/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String entryId, @PathVariable String cardId, @PathVariable String id) {
        assignmentService.deleteById(id);
        return Response.build(new AssignmentResource(entryId, cardId));
    }
}
