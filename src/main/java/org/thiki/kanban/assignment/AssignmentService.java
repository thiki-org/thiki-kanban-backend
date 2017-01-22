package org.thiki.kanban.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsCodes;
import org.thiki.kanban.card.CardsPersistence;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.InvalidParamsException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 6/16/16.
 */
@Service
public class AssignmentService {
    public static Logger logger = LoggerFactory.getLogger(AssignmentService.class);

    @Resource
    private AssignmentPersistence assignmentPersistence;
    @Resource
    private CardsPersistence cardsPersistence;
    @Resource
    private ActivityService activityService;

    @CacheEvict(value = "assignment", key = "contains('#cardId')", allEntries = true)
    public Assignment assign(final Assignment assignment, String cardId, String userName) {
        logger.info("Assigning card.assignment:{},cardId:{},userName:{}", assignment, cardId, userName);
        boolean isAlreadyAssigned = assignmentPersistence.isAlreadyAssigned(assignment.getAssignee(), cardId);
        if (isAlreadyAssigned) {
            throw new BusinessException(AssignmentCodes.ALREADY_ASSIGNED);
        }
        assignment.setCardId(cardId);
        assignment.setAuthor(userName);
        assignmentPersistence.create(assignment);
        Assignment savedAssignment = assignmentPersistence.findById(assignment.getId());
        logger.info("Assigned card successfully.savedAssignment:{}", savedAssignment);
        activityService.recordAssignment(savedAssignment);
        return savedAssignment;
    }

    public Assignment findById(String id) {
        return assignmentPersistence.findById(id);
    }

    public List<Assignment> findByCardId(String cardId) {
        logger.info("Loading assignments of the card:{}", cardId);
        Card card = cardsPersistence.findById(cardId);
        if (card == null) {
            throw new InvalidParamsException(CardsCodes.CARD_IS_NOT_EXISTS.code(), CardsCodes.CARD_IS_NOT_EXISTS.message());
        }
        List<Assignment> assignments = assignmentPersistence.findByCardId(cardId);
        logger.info("The assignments of the card [{}] are {}", cardId, assignments);
        return assignments;
    }

    @CacheEvict(value = "assignment", key = "contains('#cardId')", allEntries = true)
    public int leaveCard(String id, String cardId, String userName) {
        logger.info("Leaving card.assignmentId:{},cardId:{},userName:{}", id, cardId, userName);
        Assignment assignmentToDelete = assignmentPersistence.findById(id);
        if (assignmentToDelete == null) {
            throw new ResourceNotFoundException("assignment[" + id + "] is not found.");
        }
        logger.info("Assignment:{},", assignmentToDelete);
        activityService.recordUndoAssignment(assignmentToDelete, cardId, userName);
        return assignmentPersistence.deleteById(id);
    }
}
