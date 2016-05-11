package org.thiki.kanban.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.thiki.kanban.entry.EntriesPersistence;
import org.thiki.kanban.entry.Entry;
import org.thiki.kanban.exception.BusinessException;
import org.thiki.kanban.exception.ExceptionCode;

@Service
public class TasksService {

    @Resource
    private TasksPersistence tasksPersistence;
    @Resource
    private EntriesPersistence entriesPersistence;
    
    public Task create(Integer reporterUserId, Integer entryId, Task task) {
        task.setReporter(reporterUserId);
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null){
            throw new BusinessException(ExceptionCode.notFound.code(), "entry[" + entryId + "] is not found, task creation failed.");
        }
        Task newTask = entry.addTask(task);
        tasksPersistence.create(newTask);
        return task;
    }

    public Task updateContent(Integer taskId, String summary, String content) {
        Task task = tasksPersistence.findById(taskId);
        task.setContent(content);
        task.setSummary(summary);
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
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null){
            throw new BusinessException(ExceptionCode.notFound.code(), "entry[" + entryId + "] is not found.");
        }
        return tasksPersistence.findByEntryId(entryId);
    }

}
