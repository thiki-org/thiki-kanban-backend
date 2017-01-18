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

    public void recordCardCreation(Card newCard, String userName) {
        Activity activity = new Activity();
        activity.setProcedureId(newCard.getProcedureId());
        activity.setCardId(newCard.getId());
        activity.setSummary(newCard.toString());
        activity.setDetail(newCard.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.CARD_CREATION.code());
        activity.setOperationTypeName(ActivityType.CARD_CREATION.name());
        record(activity);
    }

    public void recordCardModification(Card modifiedCard, Card originCard, String userName) {
        Activity activity = new Activity();
        activity.setPrevProcedureId(originCard.getProcedureId());
        activity.setProcedureId(modifiedCard.getProcedureId());
        activity.setCardId(modifiedCard.getId());
        activity.setSummary(modifiedCard.diff(originCard));
        activity.setDetail(modifiedCard.diff(originCard));
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.CARD_CREATION.code());
        activity.setOperationTypeName(ActivityType.CARD_CREATION.name());
        if (!modifiedCard.getProcedureId().equals(originCard.getProcedureId())) {
            activity.setOperationTypeCode(ActivityType.CARD_MOVING.code());
            activity.setOperationTypeName(ActivityType.CARD_MOVING.name());
        }
        record(activity);
    }

    public void recordCardArchive(Card foundCard, String procedureId, String userName) {
        Activity activity = new Activity();
        activity.setPrevProcedureId(foundCard.getProcedureId());
        activity.setProcedureId(procedureId);
        activity.setCardId(foundCard.getId());
        activity.setSummary(foundCard.toString());
        activity.setDetail(foundCard.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.CARD_ARCHIVED.code());
        activity.setOperationTypeName(ActivityType.CARD_ARCHIVED.name());
        record(activity);
    }
}
