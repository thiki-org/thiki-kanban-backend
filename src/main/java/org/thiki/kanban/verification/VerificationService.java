package org.thiki.kanban.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.assignment.Assignment;
import org.thiki.kanban.assignment.AssignmentService;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.mail.MailEntity;
import org.thiki.kanban.notification.NotificationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by skytao on 03/06/17.
 */
@Service
public class VerificationService {
    public static Logger logger = LoggerFactory.getLogger(VerificationService.class);

    @Resource
    private VerificationPersistence verificationPersistence;
    @Resource
    private AcceptanceCriteriaService acceptanceCriteriaService;
    @Resource
    private CardsService cardsService;
    @Resource
    private NotificationService notificationService;
    @Resource
    private AssignmentService assignmentService;
    @Resource
    private BoardsService boardsService;

    @CacheEvict(value = "verification", key = "contains('#acceptanceCriteriaId')", allEntries = true)
    public List<Verification> addVerification(Verification verification, String acceptanceCriteriaId, String boardId, String userName) throws Exception {
        logger.info("Verify acceptance criterias,verification:{},acceptanceCriteriaId:{},userName:{}", verification, acceptanceCriteriaId, userName);
        Optional<AcceptanceCriteria> acceptanceCriteria = Optional.ofNullable(acceptanceCriteriaService.loadAcceptanceCriteriaById(acceptanceCriteriaId));
        if (!acceptanceCriteria.isPresent()) {
            throw new BusinessException(VerificationCodes.ACCEPTANCE_CRITERIA_IS_NOT_FOUND);
        }
        if (!acceptanceCriteria.get().getFinished()) {
            throw new BusinessException(VerificationCodes.ACCEPTANCE_CRITERIA_IS_NOT_FINISHED);
        }
        boolean isCardArchivedOrDone = cardsService.isCardArchivedOrDone(acceptanceCriteria.get().getCardId());
        if (isCardArchivedOrDone) {
            throw new BusinessException(VerificationCodes.CARD_HAS_ALREADY_BEEN_ARCHIVED_OR_DONE);
        }
        verificationPersistence.addVerification(verification, acceptanceCriteriaId, userName);
        if (!verification.isPassed()) {
            logger.info("Verification is not passed,sending notifications and emails.");
            sendNotificationsAndEmail(boardId, acceptanceCriteria);
        }
        return loadVerificationsByAcceptanceCriteria(acceptanceCriteriaId);
    }

    private void sendNotificationsAndEmail(String boardId, Optional<AcceptanceCriteria> acceptanceCriteria) throws Exception {
        List<Assignment> assignments = assignmentService.findByCardId(acceptanceCriteria.get().getCardId());
        List<String> assignees = new ArrayList<>();
        for (Assignment assignment : assignments) {
            assignees.add(assignment.getAssignee());
        }
        Board board = boardsService.findById(boardId);
        Card card = cardsService.findById(acceptanceCriteria.get().getCardId());
        MailEntity mailEntity = VerificationMail.newMail(acceptanceCriteria.get(), card, board);
        notificationService.sendEmailAfterNotifying(mailEntity, VerificationCodes.VERIFICATION_FAILED_EMAIL_TEMPLATE, assignees);
    }

    @Cacheable(value = "acceptanceCriteria", key = "'acceptanceCriterias'+#acceptanceCriteriaId")
    public List<Verification> loadVerificationsByAcceptanceCriteria(String acceptanceCriteriaId) {
        logger.info("Loading verifications by cardId.cardId:{}", acceptanceCriteriaId);
        List<Verification> verifications = verificationPersistence.loadVerificationsByAcceptanceCriteria(acceptanceCriteriaId);
        logger.info("Loaded verifications:{}", verifications);
        return verifications;
    }
}
