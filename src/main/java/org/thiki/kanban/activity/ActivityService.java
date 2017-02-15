package org.thiki.kanban.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.assignment.Assignment;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.cardTags.CardTag;
import org.thiki.kanban.comment.Comment;
import org.thiki.kanban.foundation.common.SequenceNumber;
import org.thiki.kanban.procedure.Procedure;

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

    public void recordCardCreation(Card newCard, Procedure procedure, String userName) {
        Activity activity = new Activity();
        activity.setProcedureId(newCard.getProcedureId());
        activity.setProcedureSnapShot(procedure.toString());
        activity.setCardId(newCard.getId());
        activity.setSummary(newCard.toString());
        activity.setDetail(newCard.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.CARD_CREATION.code());
        activity.setOperationTypeName(ActivityType.CARD_CREATION.type());
        record(activity);
    }

    public void recordCardModification(Card modifiedCard, Procedure procedure, Card originCard, String userName) {
        Activity activity = new Activity();
        activity.setPrevProcedureId(originCard.getProcedureId());
        activity.setProcedureId(modifiedCard.getProcedureId());
        activity.setProcedureSnapShot(procedure == null ? "" : procedure.toString());
        activity.setCardId(modifiedCard.getId());
        activity.setSummary(modifiedCard.diff(originCard));
        activity.setDetail(modifiedCard.diff(originCard));
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.CARD_CREATION.code());
        activity.setOperationTypeName(ActivityType.CARD_CREATION.type());
        if (!modifiedCard.getProcedureId().equals(originCard.getProcedureId())) {
            activity.setOperationTypeCode(ActivityType.CARD_MOVING.code());
            activity.setOperationTypeName(ActivityType.CARD_MOVING.type());
        }
        record(activity);
    }

    public void recordCardArchive(Card foundCard, String procedureId, Procedure prevProcedure, Procedure currentProcedure, String userName) {
        Activity activity = new Activity();
        activity.setPrevProcedureId(foundCard.getProcedureId());
        activity.setProcedureId(procedureId);
        activity.setPrevProcedureSnapShot(prevProcedure == null ? "" : prevProcedure.toString());
        activity.setProcedureSnapShot(currentProcedure == null ? "" : currentProcedure.toString());

        activity.setCardId(foundCard.getId());
        activity.setSummary(foundCard.toString());
        activity.setDetail(foundCard.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.CARD_ARCHIVED.code());
        activity.setOperationTypeName(ActivityType.CARD_ARCHIVED.type());
        record(activity);
    }

    public void recordAcceptanceCriteriaCreation(AcceptanceCriteria acceptanceCriteria, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(acceptanceCriteria.getSummary());
        activity.setDetail(acceptanceCriteria.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_CREATION.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_CREATION.type());
        record(activity);
    }

    public void recordAcceptanceCriteriaModification(AcceptanceCriteria acceptanceCriteria, AcceptanceCriteria updatedAcceptanceCriteria, String cardId, boolean isHasUnFinishedAcceptanceCriterias, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(updatedAcceptanceCriteria.diff(acceptanceCriteria));
        activity.setDetail(updatedAcceptanceCriteria.diff(acceptanceCriteria));
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_MODIFYING.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_MODIFYING.type());
        record(activity);

        if (!isHasUnFinishedAcceptanceCriterias) {
            activity.setOperationTypeCode(ActivityType.CARD_ALL_ACCEPTANCE_CRITERIAS_ARE_FINISHED.code());
            activity.setOperationTypeName(ActivityType.CARD_ALL_ACCEPTANCE_CRITERIAS_ARE_FINISHED.type());
            record(activity);
        }
    }

    public void recordAcceptanceCriteriaRemoving(String acceptanceCriteriaId, AcceptanceCriteria acceptanceCriteria, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(acceptanceCriteria.getSummary());
        activity.setDetail(acceptanceCriteria.getSummary());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_DELETING.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_MODIFYING.type());
        record(activity);
    }

    public void recordAcceptanceCriteriaResorting(List<AcceptanceCriteria> acceptanceCriterias, List<AcceptanceCriteria> resortedAcceptanceCriterias, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(resortedAcceptanceCriterias.toString());
        activity.setDetail(resortedAcceptanceCriterias.toString() + "|" + acceptanceCriterias.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.ACCEPTANCE_CRITERIA_RESORTING.code());
        activity.setOperationTypeName(ActivityType.ACCEPTANCE_CRITERIA_RESORTING.type());
        record(activity);
    }

    public void recordTags(List<CardTag> tags, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(tags.toString());
        activity.setDetail(tags.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.TAG_MODIFYING.code());
        activity.setOperationTypeName(ActivityType.TAG_MODIFYING.type());
        record(activity);
    }

    public void recordComment(Comment comment, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(comment.toString());
        activity.setDetail(comment.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.COMMENT_CREATION.code());
        activity.setOperationTypeName(ActivityType.COMMENT_CREATION.type());
        record(activity);
    }

    public void recordAssignment(Assignment savedAssignment) {
        Activity activity = new Activity();
        activity.setCardId(savedAssignment.getCardId());
        activity.setSummary(savedAssignment.toString());
        activity.setDetail(savedAssignment.toString());
        activity.setUserName(savedAssignment.getAssigner());
        activity.setOperationTypeCode(ActivityType.ASSIGNMENT.code());
        activity.setOperationTypeName(ActivityType.ASSIGNMENT.type());
        record(activity);
    }

    public void recordUndoAssignment(Assignment assignment, String cardId, String userName) {
        Activity activity = new Activity();
        activity.setCardId(cardId);
        activity.setSummary(assignment.toString());
        activity.setDetail(assignment.toString());
        activity.setUserName(userName);
        activity.setOperationTypeCode(ActivityType.UNDO_ASSIGNMENT.code());
        activity.setOperationTypeName(ActivityType.UNDO_ASSIGNMENT.type());
        record(activity);
    }
}
