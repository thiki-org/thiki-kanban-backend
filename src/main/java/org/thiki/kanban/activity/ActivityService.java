package org.thiki.kanban.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.cardTags.CardTag;
import org.thiki.kanban.foundation.common.SequenceNumber;

import javax.annotation.Resource;
import java.util.List;

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
        activity.setId(SequenceNumber.random());
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

    public void recordAcceptanceCriteriaCreation(AcceptanceCriteria acceptanceCriteria, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(acceptanceCriteria.getSummary());
        activity.setDetail(acceptanceCriteria.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_CREATION.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_CREATION.name());
        record(activity);
    }

    public void recordAcceptanceCriteriaModification(AcceptanceCriteria acceptanceCriteria, AcceptanceCriteria updatedAcceptanceCriteria, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(updatedAcceptanceCriteria.diff(acceptanceCriteria));
        activity.setDetail(updatedAcceptanceCriteria.diff(acceptanceCriteria));
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_MODIFYING.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_MODIFYING.name());
        record(activity);
    }

    public void recordAcceptanceCriteriaRemoving(String acceptanceCriteriaId, AcceptanceCriteria acceptanceCriteria, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(acceptanceCriteria.getSummary());
        activity.setDetail(acceptanceCriteria.getSummary());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_DELETING.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_MODIFYING.name());
        record(activity);
    }

    public void recordAcceptanceCriteriaResorting(List<AcceptanceCriteria> acceptanceCriterias, List<AcceptanceCriteria> resortedAcceptanceCriterias, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(resortedAcceptanceCriterias.toString());
        activity.setDetail(resortedAcceptanceCriterias.toString() + "|" + acceptanceCriterias.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_RESORTING.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_RESORTING.name());
        record(activity);
    }

    public void recordTags(List<CardTag> tags, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(tags.toString());
        activity.setDetail(tags.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.TAG_MODIFYING.code());
        activity.setOperationTypeName(ActivityType.TAG_MODIFYING.name());
        record(activity);
    }
}
