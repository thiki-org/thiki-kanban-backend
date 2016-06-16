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

    @RequestMapping(value = "/entries/{entryId}/tasks/{taskId}/assignments", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Assignment assignment, @PathVariable String entryId, @PathVariable String taskId, @RequestHeader String userId) {
        Assignment savedAssignment = assignmentService.create(assignment, taskId, userId);
        return Response.post(new AssignmentResource(savedAssignment, entryId, taskId));
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{taskId}/assignments/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String entryId, @PathVariable String taskId, @PathVariable String id) {
        Assignment foundAssignment = assignmentService.findById(id);

        return Response.build(new AssignmentResource(foundAssignment, entryId, taskId));
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{taskId}/assignments", method = RequestMethod.GET)
    public HttpEntity findByTaskId(@PathVariable String entryId, @PathVariable String taskId) {
        List<Assignment> assignmentList = assignmentService.findByTaskId(taskId);
        return Response.build(new AssignmentsResource(assignmentList, entryId, taskId));
    }

    @RequestMapping(value = "/entries/{entryId}/tasks/{taskId}/assignments/{id}", method = RequestMethod.DELETE)
    public HttpEntity deleteById(@PathVariable String entryId, @PathVariable String taskId, @PathVariable String id) {
        assignmentService.deleteById(id);
        return Response.build(new AssignmentResource(entryId, taskId));
    }
}
