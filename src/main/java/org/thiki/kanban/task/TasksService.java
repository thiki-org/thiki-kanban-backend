package org.thiki.kanban.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class TasksService {

    @Resource
    private TasksPersistence tasksPersistence;
    
    public Task create(Integer reporterUserId, Task task) {
        task.setReporter(reporterUserId);
        tasksPersistence.create(task);
        return task;
    }

}
