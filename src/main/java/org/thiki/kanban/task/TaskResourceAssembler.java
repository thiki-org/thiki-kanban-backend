package org.thiki.kanban.task;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * Created by xubitao on 04/26/16.
 */
public class TaskResourceAssembler extends ResourceAssemblerSupport<Task, TaskResource> {

    protected Object[] pathVariables;

    public TaskResourceAssembler() {
        super(TasksController.class, TaskResource.class);
    }
    
    
    public TaskResourceAssembler(Object... pathVariables){
        this();
        this.pathVariables = pathVariables;
    }
    
    public TaskResource emptyResource(){
        return buildResource(null);
    }
    
    private TaskResource buildResource(Task task){
        return new TaskResource(task);
    }
    
    @Override
    public TaskResource toResource(Task task) {
        TaskResource taskResource = buildResource(task);
        if (task != null) {
            Link selfLink = linkTo(methodOn(TasksController.class).findById(task.getId())).withSelfRel();
            taskResource.add(selfLink);
            
            Link delLink = linkTo(methodOn(TasksController.class).deleteById(task.getId())).withRel("del");
            taskResource.add(delLink);
        }
        return taskResource;
    }
    
}
