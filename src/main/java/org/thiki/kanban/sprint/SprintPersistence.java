package org.thiki.kanban.sprint;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by xubt on 04/02/2017.
 */

@Repository
public interface SprintPersistence {
    Integer create(@Param("sprint") Sprint sprint, @Param("boardId") String boardId, @Param("userName") String userName);

    Sprint findById(String id);

    boolean isExistUnArchivedSprint(String boardId);

    Integer update(@Param("sprintId") String sprintId, @Param("sprint") Sprint sprint, @Param("boardId") String boardId);

    Sprint loadActiveSprint(@Param("boardId") String boardId);
}
