package org.thiki.kanban.task;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * 
 */
public class TasksResource extends ResourceSupport {
    private List<TaskResource> tasks;

    public List<TaskResource> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResource> tasks) {
        this.tasks = tasks;
    }


}
