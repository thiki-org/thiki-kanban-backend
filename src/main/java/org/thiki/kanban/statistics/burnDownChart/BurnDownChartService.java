package org.thiki.kanban.statistics.burnDownChart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.sprint.Sprint;
import org.thiki.kanban.stage.Stage;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by winie on 2017/3/23.
 */
@Service
public class BurnDownChartService {
    public static Logger logger = LoggerFactory.getLogger(BurnDownChartService.class);
    @Resource
    private BurnDownChartPersistence burnDownChartPersistence;

    public boolean analyse(String sprintId, String boardId) {
        List<Stage> listStage= burnDownChartPersistence.findStageByBoardId(boardId);
        int statisticsCardSize=0;
        int statisticsDoneCardSize=0;
        for (Stage stage :listStage) {
            statisticsCardSize+=burnDownChartPersistence.statisticsCardSizeByStageId(stage.getId());
            if(stage.isInDoneStatus()) {
                statisticsDoneCardSize += burnDownChartPersistence.statisticsCardSizeByStageId(stage.getId());
            }
        }
        BurnDownChart burnDownChart=new BurnDownChart();
        burnDownChart.setBoardId(boardId);
        burnDownChart.setSprintId(sprintId);
        burnDownChart.setStoryPoint(statisticsCardSize);
        burnDownChart.setStoryDonePoint(statisticsDoneCardSize);
        burnDownChart.setSprintAnalyseTime(getStringDateShort());
        if(burnDownChartPersistence.findBySprintIdAndSprintAnalyseTime(sprintId,getStringDateShort())==0) {
            burnDownChartPersistence.create(burnDownChart);
        }else {
            burnDownChartPersistence.update(burnDownChart);
        }
        return true;
    }

    public  String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

   public void findAllSprint(String findDate){
       Optional<String> inFindDate= Optional.of(findDate);
       String findNowDate=inFindDate.isPresent()?findDate:getStringDateShort();
        List<Sprint>  listSprint=burnDownChartPersistence.findAllSprint(DateService.instance().StringToDate(findNowDate));
        for (Sprint sprint:listSprint) {
             analyse(sprint.getId(),sprint.getBoardId());
        }
    }

    public boolean findSprintBySprintId(String sprintId){
        Sprint  sprint=burnDownChartPersistence.findSprintBySprintId(sprintId);
        boolean analyzeIsTure= analyse(sprint.getId(),sprint.getBoardId());
        return analyzeIsTure;
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void analyseAll() {
        logger.info("Starting statistics Burn Down Chart.");
        findAllSprint(null);
        logger.info("Burn Down Chart statistics completed.");
    }


    public  List<BurnDownChart>  findBurnDownChartBySprintIdAndBoardId(String boardId,String sprintId){
        return burnDownChartPersistence.findBurnDownChartBySprintIdAndBoardId(boardId,sprintId);
    }

}
