package org.thiki.kanban.verification;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteria;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaService;
import org.thiki.kanban.assignment.Assignment;
import org.thiki.kanban.assignment.AssignmentService;
import org.thiki.kanban.board.Board;
import org.thiki.kanban.board.BoardsService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.notification.NotificationService;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by xubt on 06/03/2017.
 */
public class VerificationServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private VerificationService verificationService;
    @Mock
    private AcceptanceCriteriaService acceptanceCriteriaService;
    @Mock
    private VerificationPersistence verificationPersistence;
    @Mock
    private NotificationService notificationService;
    @Mock
    private AssignmentService assignmentService;
    @Mock
    private BoardsService boardsService;
    @Mock
    private CardsService cardsService;
    private String acceptanceCriteriaId = "foo";
    private String userName = "fee";
    private String boardId = "board-fooId";
    private String cardId = "card-fooId";

    @Test
    public void should_failed_if_acceptance_criteria_is_not_found() throws Exception {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(VerificationCodes.ACCEPTANCE_CRITERIA_IS_NOT_FOUND.message());

        AcceptanceCriteria expectedAcceptanceCriteria = new AcceptanceCriteria();
        expectedAcceptanceCriteria.setId(acceptanceCriteriaId);
        expectedAcceptanceCriteria.setFinished(false);

        Verification verification = new Verification();
        verification.setAcceptanceCriteriaId(acceptanceCriteriaId);

        when(acceptanceCriteriaService.loadAcceptanceCriteriaById(eq(acceptanceCriteriaId))).thenReturn(null);

        verificationService.addVerification(verification, acceptanceCriteriaId, boardId, userName);
    }

    @Test
    public void should_failed_if_acceptance_criteria_is_not_finished() throws Exception {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(VerificationCodes.ACCEPTANCE_CRITERIA_IS_NOT_FINISHED.message());

        AcceptanceCriteria expectedAcceptanceCriteria = new AcceptanceCriteria();
        expectedAcceptanceCriteria.setId(acceptanceCriteriaId);
        expectedAcceptanceCriteria.setFinished(false);

        Verification verification = new Verification();
        verification.setAcceptanceCriteriaId(acceptanceCriteriaId);

        when(acceptanceCriteriaService.loadAcceptanceCriteriaById(eq(acceptanceCriteriaId))).thenReturn(expectedAcceptanceCriteria);

        verificationService.addVerification(verification, acceptanceCriteriaId, boardId, userName);
    }

    @Test
    public void should_failed_if_the_card_acceptance_criteria_belongs_to_has_been_archived_or_done() throws Exception {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(VerificationCodes.CARD_HAS_ALREADY_BEEN_ARCHIVED_OR_DONE.message());

        AcceptanceCriteria expectedAcceptanceCriteria = new AcceptanceCriteria();
        expectedAcceptanceCriteria.setId(acceptanceCriteriaId);
        expectedAcceptanceCriteria.setFinished(true);

        Verification verification = new Verification();
        verification.setAcceptanceCriteriaId(acceptanceCriteriaId);

        when(cardsService.isCardArchivedOrDone(expectedAcceptanceCriteria.getCardId())).thenReturn(true);
        when(acceptanceCriteriaService.loadAcceptanceCriteriaById(eq(acceptanceCriteriaId))).thenReturn(expectedAcceptanceCriteria);

        verificationService.addVerification(verification, acceptanceCriteriaId, boardId, userName);
    }

    @Test
    public void should_send_notifications_if_verification_is_not_passed() throws Exception {
        AcceptanceCriteria expectedAcceptanceCriteria = new AcceptanceCriteria();
        expectedAcceptanceCriteria.setId(acceptanceCriteriaId);
        expectedAcceptanceCriteria.setCardId(cardId);
        expectedAcceptanceCriteria.setFinished(true);

        Verification verification = new Verification();
        verification.setAcceptanceCriteriaId(acceptanceCriteriaId);
        verification.setIsPassed(VerificationCodes.IS_NOT_PASSED);

        Assignment assignment = new Assignment();
        when(assignmentService.findByCardId(expectedAcceptanceCriteria.getCardId())).thenReturn(Arrays.asList(assignment));

        when(cardsService.isCardArchivedOrDone(expectedAcceptanceCriteria.getCardId())).thenReturn(false);
        when(acceptanceCriteriaService.loadAcceptanceCriteriaById(eq(acceptanceCriteriaId))).thenReturn(expectedAcceptanceCriteria);
        when(verificationPersistence.addVerification(any(), any(), any())).thenReturn(1);
        when(boardsService.findById(boardId)).thenReturn(new Board());
        when(cardsService.findById(cardId)).thenReturn(new Card());
        doNothing().when(notificationService).sendEmailAfterNotifying(any(), eq(VerificationCodes.VERIFICATION_FAILED_EMAIL_TEMPLATE), anyList());
        doNothing().when(acceptanceCriteriaService).verify(eq(cardId), eq(acceptanceCriteriaId), any());
        verificationService.addVerification(verification, acceptanceCriteriaId, boardId, userName);

        verify(notificationService).sendEmailAfterNotifying(any(), eq(VerificationCodes.VERIFICATION_FAILED_EMAIL_TEMPLATE), anyList());
        verify(acceptanceCriteriaService).verify(eq(cardId), eq(acceptanceCriteriaId), any());
    }
}
