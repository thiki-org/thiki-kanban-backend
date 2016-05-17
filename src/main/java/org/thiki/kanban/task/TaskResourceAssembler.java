package org.thiki.kanban.task;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 04/26/16.
 */
public class TaskResourceAssembler extends ResourceAssemblerSupport<Task, TaskResource> {

    protected Object[] pathVariables;

    public TaskResourceAssembler() {
        super(TasksController.class, TaskResource.class);
    }


    public TaskResourceAssembler(Object... pathVariables) {
        this();
        this.pathVariables = pathVariables;
    }

    public TaskResource emptyResource() {
        return buildResource(null);
    }

    private TaskResource buildResource(Task task) {
        return new TaskResource(task);
    }

    @Override
    public TaskResource toResource(Task task) {
        TaskResource taskResource = buildResource(task);
        if (task != null) {
            Link selfLink = linkTo(methodOn(TasksController.class).findById(pathVariables[0].toString(), task.getId())).withSelfRel();
            taskResource.add(selfLink);
        }
        Link tasksLink = linkTo(methodOn(TasksController.class).findByEntryId(pathVariables[0].toString())).withRel("tasks");

        taskResource.add(tasksLink);
        return taskResource;
    }
}
