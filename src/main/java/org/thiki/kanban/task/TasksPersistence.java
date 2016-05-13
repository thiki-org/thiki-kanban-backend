package org.thiki.kanban.task;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TasksPersistence {

    void create(Task task);

    Task findById(Integer taskId);

    void update(Task task);

    List<Task> findByEntryId(Integer entryId);

}
