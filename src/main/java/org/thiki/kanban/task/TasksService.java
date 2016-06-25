package org.thiki.kanban.task;

import org.springframework.stereotype.Service;
import org.thiki.kanban.entry.EntriesPersistence;
import org.thiki.kanban.entry.Entry;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

@Service
public class TasksService {

    @Resource
    private TasksPersistence tasksPersistence;

    @Resource
    private EntriesPersistence entriesPersistence;

    public Task create(Integer reporterUserId, String entryId, Task task) {
        task.setReporter(reporterUserId);
        task.setEntryId(entryId);
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null) {
            throw new ResourceNotFoundException("entry[" + entryId + "] is not found.");
        }
        Task newTask = entry.addTask(task);
        tasksPersistence.create(newTask);
        return tasksPersistence.findById(newTask.getId());
    }

    public Task update(String taskId, Task currentTask) {
        Task originTask = findById(taskId);

        tasksPersistence.update(taskId, currentTask);

        if (isTaskMovedAcrossEntry(currentTask, originTask)) {
            tasksPersistence.resortTargetEntry(originTask.getId(), currentTask.getEntryId(), currentTask.getOrderNumber());
            tasksPersistence.resortOriginEntry(originTask.getId(), originTask.getEntryId(), originTask.getOrderNumber());
        }
        if (isTaskMovedWithinOriginEntry(currentTask, originTask)) {
            int increment = currentTask.getOrderNumber() > originTask.getOrderNumber() ? 1 : 0;
            tasksPersistence.resortOrder(originTask.getId(), originTask.getEntryId(), originTask.getOrderNumber(), currentTask.getOrderNumber(), increment);
        }
        return tasksPersistence.findById(taskId);
    }

    private boolean isTaskMovedWithinOriginEntry(Task currentTask, Task originTask) {
        return (currentTask.getEntryId().equals(originTask.getEntryId())) && (!currentTask.getOrderNumber().equals(originTask.getOrderNumber()));
    }

    private boolean isTaskMovedAcrossEntry(Task currentTask, Task originTask) {
        return !currentTask.getEntryId().equals(originTask.getEntryId());
    }

    public int deleteById(String taskId) {
        loadAndValidateTask(taskId);
        return tasksPersistence.deleteById(taskId);
    }

    public List<Task> findByEntryId(String entryId) {
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null) {
            throw new ResourceNotFoundException(MessageFormat.format("entry[{0}] is not found.", entryId));
        }
        return tasksPersistence.findByEntryId(entryId);
    }

    public Task findById(String taskId) {
        return loadAndValidateTask(taskId);
    }

    private Task loadAndValidateTask(String taskId) {
        Task foundTask = tasksPersistence.findById(taskId);
        if (foundTask == null) {
            throw new ResourceNotFoundException(MessageFormat.format("task[{0}] is not found.", taskId));
        }
        return foundTask;
    }
}
