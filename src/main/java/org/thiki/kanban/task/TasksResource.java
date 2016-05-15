package org.thiki.kanban.task;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

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
