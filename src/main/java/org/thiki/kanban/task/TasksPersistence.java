package org.thiki.kanban.task;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TasksPersistence {

    void create(Task task);

    Task findById(String taskId);

    void update(Task task);

    List<Task> findByEntryId(String entryId);

    Integer deleteById(@Param("id") String id);

    Integer resortOrder(Map<String, Object> resort);
}
