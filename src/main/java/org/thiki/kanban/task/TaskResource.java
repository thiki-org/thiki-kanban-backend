package org.thiki.kanban.task;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 任务的资源DTO
 *
 * @author joeaniu
 */
public class TaskResource extends RestResource {
    public TaskResource(Task task, String entryId) {
        this.domainObject = task;
        if (task != null) {
            Link selfLink = linkTo(methodOn(TasksController.class).findById(entryId, task.getId())).withSelfRel();
            this.add(selfLink);
        }

        this.add(linkTo(methodOn(TasksController.class).findByEntryId(entryId)).withRel("tasks"));

    }

    public TaskResource(String entryId) {
        this.add(linkTo(methodOn(TasksController.class).findByEntryId(entryId)).withRel("tasks"));
    }
}
