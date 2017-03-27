package org.thiki.kanban.statistics.burnDownChart;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.thiki.kanban.sprint.Sprint;
import org.thiki.kanban.stage.Stage;

import java.util.Date;
import java.util.List;

/**
 * Created by winie on 2017/3/23.
 */
@Repository
public interface BurnDownChartPersistence {

    void create(@Param("burnDownChart") BurnDownChart burnDownChart);
    void update(@Param("burnDownChart") BurnDownChart burnDownChart);
    List<Sprint> findAllSprint(@Param("nowDate") Date nowDate);
    Sprint findSprintBySprintId(@Param("sprintId") String sprintId);
    List<Stage> findStageByBoardId(@Param("boardId") String boardId);
    int statisticsCardSizeByStageId(@Param("stageId") String stageId);
    int findBySprintIdAndSprintAnalyseTime(@Param("sprintId") String sprintId, @Param("sprintAnalyseTime") String sprintAnalyseTime);

}
