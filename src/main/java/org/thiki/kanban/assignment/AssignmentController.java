package org.thiki.kanban.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by xubitao on 6/16/16.
 */
@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/assignments", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Assignment assignment, @PathVariable String procedureId, @PathVariable String cardId, @RequestHeader String userName) throws IOException {
        Assignment savedAssignment = assignmentService.assign(assignment, cardId, userName);
        return Response.post(new AssignmentResource(savedAssignment, procedureId, cardId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/assignments/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String procedureId, @PathVariable String cardId, @PathVariable String id) throws IOException {
        Assignment foundAssignment = assignmentService.findById(id);

        return Response.build(new AssignmentResource(foundAssignment, procedureId, cardId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/assignments", method = RequestMethod.GET)
    public HttpEntity findByCardId(@PathVariable String procedureId, @PathVariable String cardId) throws IOException {
        List<Assignment> assignmentList = assignmentService.findByCardId(cardId);
        return Response.build(new AssignmentsResource(assignmentList, procedureId, cardId));
    }

    @RequestMapping(value = "/procedures/{procedureId}/cards/{cardId}/assignments/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String procedureId, @PathVariable String cardId, @PathVariable String id) throws IOException {
        assignmentService.deleteById(id);
        return Response.build(new AssignmentResource(procedureId, cardId));
    }
}
