package org.thiki.kanban.stage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface StagesPersistence {
    Integer create(@Param("stage") Stage stage, @Param("userName") String userName, @Param("boardId") String boardId);

    Stage findById(@Param("stageId") String stageId);

    List<Stage> loadByBoardIdAndType(@Param("boardId") String boardId, @Param("viewType") String type);

    Integer update(@Param("stageId") String stageId, @Param("stage") Stage stage);

    Integer deleteById(@Param("stageId") String stageId);

    Integer resort(Stage stage);

    boolean uniqueTitle(@Param("boardId") String boardId, @Param("title") String title);

    boolean isDoneStageAlreadyExist(@Param("stageId") String stageId, @Param("boardId") String boardId);

    Stage findDoneStage(@Param("boardId") String boardId);

    boolean hasNewArchivedStageExist(Stage archivedStage);

    Stage findStageByStatus(@Param("boardId") String boardId, @Param("status") Integer status);

    Integer countCardsNumber(@Param("stageId") String stageId);
}
