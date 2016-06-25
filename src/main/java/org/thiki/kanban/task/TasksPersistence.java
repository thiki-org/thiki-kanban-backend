package org.thiki.kanban.task;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksPersistence {

    void create(Task task);

    Task findById(String taskId);

    void update(@Param("id") String taskId, @Param("task") Task task);

    List<Task> findByEntryId(String entryId);

    Integer deleteById(@Param("id") String id);

    Integer resortTargetEntry(@Param("taskId") String taskId, @Param("currentEntryId") String currentEntryId, @Param("currentOrderNumber") Integer currentOrderNumber);

    Integer resortOriginEntry(@Param("taskId") String taskId, @Param("originEntryId") String originEntryId, @Param("originOrderNumber") Integer originOrderNumber);

    Integer resortOrder(@Param("taskId") String taskId, @Param("entryId") String entryId, @Param("originOrderNumber") Integer originOrderNumber, @Param("currentOrderNumber") Integer currentOrderNumber, @Param("increment") int increment);
}
