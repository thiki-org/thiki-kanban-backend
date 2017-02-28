package org.thiki.kanban.board;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubitao on 05/26/16.
 */

@Repository
public interface BoardsPersistence {
    Integer create(Board board);

    Board findById(@Param("id") String id);

    Integer update(Board board);

    Integer deleteById(@Param("id") String id, @Param("userName") String userName);

    boolean unique(@Param("id") String id, @Param("name") String boardName, @Param("userName") String userName);

    List<Board> findProjectsBoards(String projectId);

    List<Board> findPersonalBoards(String TeamId);
}
