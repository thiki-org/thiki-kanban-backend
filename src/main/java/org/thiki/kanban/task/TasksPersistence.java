package org.thiki.kanban.task;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksPersistence {

    void create(Task task);

    Task findById(String taskId);

    void update(Task task);

    List<Task> findByEntryId(String entryId);

}
