package org.thiki.kanban.assignment;

import org.springframework.hateoas.Link;
import org.thiki.kanban.entry.EntriesController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.task.TasksController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 6/16/16.
 */
public class AssignmentResource extends RestResource {
    public AssignmentResource(Assignment assignment, String entryId, String taskId) {
        this.domainObject = assignment;
        if (assignment != null) {
            Link selfLink = linkTo(methodOn(AssignmentController.class).findById(entryId, taskId, assignment.getId())).withSelfRel();
            this.add(selfLink);

            Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByTaskId(entryId, taskId)).withRel("assignments");
            this.add(assignmentsLink);

            Link taskLink = linkTo(methodOn(TasksController.class).findById(entryId, taskId)).withRel("task");
            this.add(taskLink);
        }
        this.add(linkTo(methodOn(EntriesController.class).loadAll(entryId)).withRel("all"));
    }

    public AssignmentResource(String entryId, String taskId) {
        Link assignmentsLink = linkTo(methodOn(AssignmentController.class).findByTaskId(entryId, taskId)).withRel("assignments");
        this.add(assignmentsLink);

        Link taskLink = linkTo(methodOn(TasksController.class).findById(entryId, taskId)).withRel("task");
        this.add(taskLink);
    }
}
