package org.thiki.kanban.task;

import org.springframework.stereotype.Service;
import org.thiki.kanban.entry.EntriesPersistence;
import org.thiki.kanban.entry.Entry;
import org.thiki.kanban.foundation.common.Sequence;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TasksService {

    @Resource
    private TasksPersistence tasksPersistence;
    @Resource
    private EntriesPersistence entriesPersistence;

    public Task create(Integer reporterUserId, String entryId, Task task) {
        task.setId(Sequence.generate());
        task.setReporter(reporterUserId);
        task.setEntryId(entryId);
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null) {
            throw new ResourceNotFoundException("entry[" + entryId + "] is not found, task creation failed.");
        }
        Task newTask = entry.addTask(task);
        tasksPersistence.create(newTask);
        return task;
    }

    public Task updateContent(String taskId, Task task) {
        Task taskToUpdate = tasksPersistence.findById(taskId);
        if (taskToUpdate == null) {
            throw new ResourceNotFoundException("entry[" + taskId + "] is not found, task update failed.");
        }
        task.setId(taskId);
        tasksPersistence.update(task);
        return task;
    }

    public int deleteById(String id) {
        Task taskToDelete = tasksPersistence.findById(id);
        if (taskToDelete == null) {
            throw new ResourceNotFoundException("task[" + id + "] is not found.");
        }
        return tasksPersistence.deleteById(id);
    }

    public List<Task> findByEntryId(String entryId) {
        Entry entry = entriesPersistence.findById(entryId);
        if (entry == null) {
            throw new ResourceNotFoundException("entry[" + entryId + "] is not found.");
        }
        return tasksPersistence.findByEntryId(entryId);
    }
}
