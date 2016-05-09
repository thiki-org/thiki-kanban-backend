package org.thiki.kanban.task;

import org.springframework.stereotype.Repository;

@Repository
public interface TasksPersistence {

    void create(Task task);

}
