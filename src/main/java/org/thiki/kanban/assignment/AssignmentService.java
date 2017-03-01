package org.thiki.kanban.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsCodes;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.InvalidParamsException;
import org.thiki.kanban.notification.NotificationService;
import org.thiki.kanban.user.User;
import org.thiki.kanban.user.UsersService;

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
    private CardsService cardsService;
    @Resource
    private ActivityService activityService;
    @Resource
    private NotificationService notificationService;
    @Resource
    private UsersService usersService;
    @Resource
    private BoardsService boardsService;

    @CacheEvict(value = "assignment", key = "contains('#cardId')", allEntries = true, beforeInvocation = true)
    public List<Assignment> assign(final List<Assignment> assignments, String cardId, String boardId, String userName) throws Exception {
        logger.info("Assigning card.assignments:{},cardId:{},userName:{}", assignments, cardId, userName);
        Card card = cardsService.findById(cardId);
        if (card == null) {
            throw new InvalidParamsException(CardsCodes.CARD_IS_NOT_EXISTS.code(), CardsCodes.CARD_IS_NOT_EXISTS.message());
        }
        boolean isArchived = cardsService.isArchived(cardId);
        if (isArchived) {
            throw new BusinessException(AssignmentCodes.CARD_IS_ALREADY_ARCHIVED);
        }
        Board board = boardsService.findById(boardId);
        List<Assignment> originAssignments = findByCardId(cardId);
        for (Assignment assignment : assignments) {
            boolean isAlreadyAssigned = assignmentPersistence.isAlreadyAssigned(assignment.getAssignee(), cardId);
            if (!isAlreadyAssigned) {
                assignment.setCardId(cardId);
                assignment.setAuthor(userName);
                assignmentPersistence.create(assignment);
                User sender = usersService.findByName(assignment.getAssigner());
                User receiver = usersService.findByName(assignment.getAssignee());
                AssignmentMail assignmentMail = AssignmentMail.newMail(assignment, sender, receiver, card, board, false);
                notificationService.sendEmailAfterNotifying(assignmentMail);
            }
        }
        for (Assignment originAssignment : originAssignments) {
            if (!assignments.contains(originAssignment)) {
                assignmentPersistence.deleteById(originAssignment.getId());
                if (!originAssignment.isSelfAssignment(userName)) {
                    User sender = usersService.findByName(originAssignment.getAssigner());
                    User receiver = usersService.findByName(originAssignment.getAssignee());
                    AssignmentMail assignmentMail = AssignmentMail.newMail(originAssignment, sender, receiver, card, board, true);
                    notificationService.sendEmailAfterNotifying(assignmentMail);
                }
            }
        }
        List<Assignment> savedAssignments = findByCardId(cardId);
        logger.info("Assigned card successfully.savedAssignments:{}", savedAssignments);
        activityService.recordAssignments(savedAssignments, cardId, userName);
        return savedAssignments;
    }

    @Cacheable(value = "assignment", key = "'assignments'+#cardId")
    public List<Assignment> findByCardId(String cardId) {
        logger.info("Loading assignments of the card:{}", cardId);
        List<Assignment> assignments = assignmentPersistence.findByCardId(cardId);
        logger.info("The assignments of the card [{}] are {}", cardId, assignments);
        return assignments;
    }
}
