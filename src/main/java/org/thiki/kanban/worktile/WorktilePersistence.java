package org.thiki.kanban.worktile;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubt on 11/1/16.
 */

@Repository
public interface WorktilePersistence {
    Integer create(WorktileCards worktileCards);

    WorktileCards findById(@Param("id") String id);

    List<WorktileCards> loadByUserName(String userName);

    Integer update(WorktileCards worktileCards);

    Integer deleteById(@Param("id") String id);

    List<WorktileCards> findByUserId(String userId);

    List<WorktileCards> findByTeamId(String TeamId);

    boolean unique(@Param("id") String id, @Param("name") String boardName, @Param("userName") String userName);

    String findTeamId(String boardId);
}
