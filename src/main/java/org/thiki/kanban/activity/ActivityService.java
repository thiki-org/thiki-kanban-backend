package org.thiki.kanban.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.Card;

import javax.annotation.Resource;

/**
 * Created by xubt on 16/01/2017.
 */
@Service
public class ActivityService {
    private final static Logger logger = LoggerFactory.getLogger(ActivityService.class);
    @Resource
    private ActivityPersistence activityPersistence;

    @CacheEvict(value = "activity", key = "{'activity'+#activity.cardId}", allEntries = true)
    public void record(final Activity activity) {
        logger.info("Recording operation:{}", activity);
        activityPersistence.record(activity);
    }

    public void record(ActivityType activityType, Card card, String userName) {
        Activity activity = new Activity();
        activity.setProcedureId(card.getProcedureId());
        activity.setCardId(card.getId());
        activity.setSummary(card.toString());
        activity.setDetail(card.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(activityType.code());
        activity.setOperationTypeName(activityType.name());
        record(activity);
    }

    public void record(ActivityType activityType, Card card, Card originCard, String procedureId, String userName) {
        Activity activity = new Activity();
        activity.setProcedureId(procedureId);
        activity.setCardId(card.getId());
        activity.setSummary(card.toString());
        activity.setDetail("Origin card:" + originCard.toString() + "\nNew card:" + card.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(activityType.code());
        activity.setOperationTypeName(activityType.name());
        record(activity);
    }
}
