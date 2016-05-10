package org.thiki.kanban.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class TasksService {

    @Resource
    private TasksPersistence tasksPersistence;
    
    public Task create(Integer reporterUserId, Integer entryId, Task task) {
        task.setReporter(reporterUserId);
        task.setEntryId(entryId);
        tasksPersistence.create(task);
        return task;
    }

    public Task updateContent(Integer taskId, Task changedTask) {
        Task task = tasksPersistence.findById(taskId);
        task.setContent(changedTask.getContent());
        task.setSummary(changedTask.getSummary());
        tasksPersistence.update(task);
        return task;
    }

    public Task assign(Integer taskId, Integer assignee) {
        Task task = tasksPersistence.findById(taskId);
        task.setAssignee(assignee);
        tasksPersistence.update(task);
        return task;
    }

    public List<Task> findByEntryId(Integer entryId) {
        // TODO Auto-generated method stub
        return null;
    }

}
