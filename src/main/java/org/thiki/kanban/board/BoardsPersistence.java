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

    List<Board> loadAll();

    Integer update(Board board);

    Integer deleteById(@Param("id") String id);

    Boolean checkRedundancy(Board board);

    List<Board> findByUserId(String userId);
}
