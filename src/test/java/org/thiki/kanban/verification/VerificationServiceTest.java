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
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

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
    private CardsService cardsService;
    private String acceptanceCriteriaId = "foo";
    private String userName = "fee";

    @Test
    public void should_failed_if_acceptance_criteria_is_not_finished() {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(VerificationCodes.ACCEPTANCE_CRITERIA_IS_NOT_FINISHED.message());

        AcceptanceCriteria expectedAcceptanceCriteria = new AcceptanceCriteria();
        expectedAcceptanceCriteria.setId(acceptanceCriteriaId);
        expectedAcceptanceCriteria.setFinished(false);

        Verification verification = new Verification();
        verification.setAcceptanceCriteriaId(acceptanceCriteriaId);

        when(acceptanceCriteriaService.loadAcceptanceCriteriaById(eq(acceptanceCriteriaId))).thenReturn(expectedAcceptanceCriteria);

        verificationService.addVerification(verification, acceptanceCriteriaId, userName);
    }
}