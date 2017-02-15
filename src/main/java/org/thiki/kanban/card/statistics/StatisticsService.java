package org.thiki.kanban.card.statistics;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thiki.kanban.activity.Activity;
import org.thiki.kanban.activity.ActivityService;
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
    @Resource
    private CardsPersistence cardsPersistence;
    @Resource
    private ActivityService activityService;

    @Scheduled(cron = "* * * * * *")
    public void analyse() {
        List<Card> cards = cardsPersistence.loadUnArchivedCards();
        for (Card card : cards) {
            List<Activity> activities = activityService.loadActivitiesByCard(card.getId());
            double elapsedDays = calculateElapsedDays(activities);
            card.setElapsedDays(elapsedDays);
            cardsPersistence.modify(card.getId(), card);
        }
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
            if (activity.isMoveOut(moveInActivity.getProcedureId())) {
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
