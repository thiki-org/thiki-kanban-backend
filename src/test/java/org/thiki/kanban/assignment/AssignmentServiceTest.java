package org.thiki.kanban.assignment;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsService;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by xubt on 26/02/2017.
 */
public class AssignmentServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    private AssignmentService assignmentService;
    @Mock
    private AssignmentPersistence assignmentPersistence;
    @Mock
    private CardsService cardsService;
    @Mock
    private ActivityService activityService;

    private String userName = "someone";
    private String cardId = "cardId-foo";


    @Before
    public void setUp() {
        doNothing().when(activityService).recordAssignments(any(), any(), any());
        when(cardsService.findById(cardId)).thenReturn(new Card());
    }

    @Test
    public void should_return_all_the_assignments() {
        Assignment assignment1 = JSON.toJavaObject(JSON.parseObject("{\"assignee\":\"assignee-foo1\",\"assigner\":\"assigner-foo1\",\"cardId\":\"cardId-foo\"}"), Assignment.class);
        Assignment assignment2 = JSON.toJavaObject(JSON.parseObject("{\"assignee\":\"assignee-foo2\",\"assigner\":\"assigner-foo2\",\"cardId\":\"cardId-foo\"}"), Assignment.class);
        List<Assignment> assignmentList = Arrays.asList(assignment1, assignment2);

        int expectedSize = 2;
        when(assignmentPersistence.isAlreadyAssigned(any(), any())).thenReturn(false);
        when(assignmentPersistence.create(any())).thenReturn(1);
        when(assignmentPersistence.findByCardId(eq(cardId))).thenReturn(assignmentList);

        List<Assignment> savedAssignments = assignmentService.assign(assignmentList, cardId, userName);
        assertEquals(expectedSize, savedAssignments.size());
    }

    @Test
    public void should_remove_unSubmit_assignments() {
        Assignment assignment1 = JSON.toJavaObject(JSON.parseObject("{\"assignee\":\"assignee-foo1\",\"assigner\":\"assigner-foo1\",\"cardId\":\"cardId-foo\"}"), Assignment.class);
        Assignment assignment2 = JSON.toJavaObject(JSON.parseObject("{\"assignee\":\"assignee-foo2\",\"assigner\":\"assigner-foo2\",\"cardId\":\"cardId-foo\"}"), Assignment.class);
        Assignment assignment3 = JSON.toJavaObject(JSON.parseObject("{\"assignee\":\"assignee-foo3\",\"assigner\":\"assigner-foo3\",\"cardId\":\"cardId-foo\"}"), Assignment.class);
        List<Assignment> originAssignmentList = Arrays.asList(assignment1, assignment2, assignment3);
        List<Assignment> assignmentList = Arrays.asList(assignment2, assignment3);

        int expectedSize = 2;
        when(assignmentPersistence.isAlreadyAssigned(any(), any())).thenReturn(false);
        when(assignmentPersistence.create(any())).thenReturn(1);
        when(assignmentPersistence.findByCardId(eq(cardId))).thenReturn(originAssignmentList).thenReturn(assignmentList);

        List<Assignment> savedAssignments = assignmentService.assign(assignmentList, cardId, userName);
        assertEquals(expectedSize, savedAssignments.size());
    }
}
