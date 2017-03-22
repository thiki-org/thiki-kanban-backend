package org.thiki.kanban.comment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.thiki.kanban.acceptanceCriteria.AcceptanceCriteriaCodes;
import org.thiki.kanban.card.CardsService;
import org.thiki.kanban.foundation.exception.BusinessException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by xubt on 22/03/2017.
 */
public class CommentServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CardsService cardsService;
    private String cardId = "card-fooId";
    private String userName = "someone";

    @Test
    public void should_adding_comment_failed_when_card_is_done_or_archived() {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage(AcceptanceCriteriaCodes.CARD_WAS_ALREADY_DONE_OR_ARCHIVED.message());

        when(cardsService.isCardArchivedOrDone(cardId)).thenReturn(true);
        commentService.addComment(userName, cardId, any());
    }
}
