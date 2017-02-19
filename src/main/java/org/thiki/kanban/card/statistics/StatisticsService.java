package org.thiki.kanban.card.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.activity.Activity;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.activity.ActivityType;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsPersistence;
import org.thiki.kanban.foundation.common.date.DateService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by xubt on 15/02/2017.
 */
@Service
public class StatisticsService {
    public static Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    @Resource
    private CardsPersistence cardsPersistence;
    @Resource
    private ActivityService activityService;

    @Scheduled(cron = "00 2,9,12,15,20 * * * *")
    public void analyse() {
        logger.info("Starting statistics elapsed days.");
        List<Card> cards = cardsPersistence.loadUnArchivedCards();
        for (Card card : cards) {
            List<Activity> activities = activityService.loadActivitiesByCard(ActivityType.CARD_MOVING.code(), card.getId());
            double elapsedDays = calculateElapsedDays(activities);
            card.setElapsedDays(elapsedDays);
            cardsPersistence.modify(card.getId(), card);
        }
        logger.info("Elapsed days statistics completed.");
    }

    public double calculateElapsedDays(List<Activity> activities) {
        int totalElapsedHours = 0;
        for (Activity activity : activities) {
            if (activityService.isMoveInProcess(activity)) {
                Date moveInProcessTime = DateService.instance().StringToDate(activity.getCreationTime());
                Date moveOutProcessTime = findMoveOutProcessTime(activity, activities);

                int elapsedHours = DateService.instance().getIntervalHours(moveInProcessTime, moveOutProcessTime);
                totalElapsedHours += elapsedHours;
            }
        }
        return convertHoursToDays(totalElapsedHours);
    }

    private Date findMoveOutProcessTime(Activity moveInActivity, List<Activity> activities) {
        for (Activity activity : activities) {
            if (activity.isMoveOut(moveInActivity.getStageId())) {
                return DateService.instance().StringToDate(activity.getCreationTime());
            }
        }
        return new Date();

    }

    private double convertHoursToDays(int hours) {
        int days = hours / 24;
        int oddHours = hours % 24;
        if (hours == 0) {
            return 0;
        }
        if (oddHours < 4) {
            return days + 0.5;
        }
        if (oddHours < 24) {
            return days + 1;
        }
        return days;
    }
}
