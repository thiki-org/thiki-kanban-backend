package org.thiki.kanban.card.statistics;

import com.alibaba.fastjson.JSON;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.thiki.kanban.activity.Activity;
import org.thiki.kanban.activity.ActivityService;
import org.thiki.kanban.procedure.ProceduresService;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by xubt on 15/02/2017.
 */
public class StatisticsServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private ProceduresService proceduresService;
    @Mock
    private ActivityService activityService;
    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    public void shouldReturnHalfOfADay() {
        double expectedElapsedDays = 0.5;
        Activity moveInActivity = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo1\",\"cardId\":\"foo-cardId\",\"prevProcedureId\":\"undoing-procedureId1\",\"procedureId\":\"doing-procedureId\",\"creationTime\":\"2017-02-15 09:57:35\"}"), Activity.class);
        Activity moveOutActivity = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo2\",\"cardId\":\"foo-cardId2\",\"prevProcedureId\":\"doing-procedureId\",\"procedureId\":\"undoing-procedureId2\",\"creationTime\":\"2017-02-15 11:57:35\"}"), Activity.class);
        List<Activity> activities = Arrays.asList(moveInActivity, moveOutActivity);
        when(activityService.isMoveInProcess(any())).thenReturn(true).thenReturn(false);
        double actualElapsedDays = statisticsService.calculateElapsedDays(activities);

        assertEquals(expectedElapsedDays, actualElapsedDays);
    }

    @Test
    public void shouldReturn1_5Days() {
        double expectedElapsedDays = 1.5;
        Activity moveInActivity1 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo1\",\"cardId\":\"foo-cardId\",\"prevProcedureId\":\"undoing-procedureId1\",\"procedureId\":\"doing-procedureId1\",\"creationTime\":\"2017-02-15 09:57:35\"}"), Activity.class);
        Activity moveOutActivity1 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo2\",\"cardId\":\"foo-cardId2\",\"prevProcedureId\":\"doing-procedureId1\",\"procedureId\":\"undoing-procedureId1\",\"creationTime\":\"2017-02-15 11:57:35\"}"), Activity.class);
        Activity moveInActivity2 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo3\",\"cardId\":\"foo-cardId3\",\"prevProcedureId\":\"undoing-procedureId2\",\"procedureId\":\"doing-procedureId2\",\"creationTime\":\"2017-02-15 14:57:35\"}"), Activity.class);
        Activity moveOutActivity2 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo4\",\"cardId\":\"foo-cardId4\",\"prevProcedureId\":\"doing-procedureId2\",\"procedureId\":\"undoing-procedureId2\",\"creationTime\":\"2017-02-16 13:57:35\"}"), Activity.class);
        List<Activity> activities = Arrays.asList(moveInActivity1, moveOutActivity1, moveInActivity2, moveOutActivity2);
        when(activityService.isMoveInProcess(any())).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        double actualElapsedDays = statisticsService.calculateElapsedDays(activities);

        assertEquals(expectedElapsedDays, actualElapsedDays);
    }
}
