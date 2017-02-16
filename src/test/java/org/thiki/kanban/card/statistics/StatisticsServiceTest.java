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
import org.thiki.kanban.stage.StagesService;

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
    private StagesService stagesService;
    @Mock
    private ActivityService activityService;
    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    public void shouldReturnHalfOfADay() {
        double expectedElapsedDays = 0.5;
        Activity moveInActivity = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo1\",\"cardId\":\"foo-cardId\",\"prevStageId\":\"undoing-stageId1\",\"stageId\":\"doing-stageId\",\"creationTime\":\"2017-02-15 09:57:35\"}"), Activity.class);
        Activity moveOutActivity = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo2\",\"cardId\":\"foo-cardId2\",\"prevStageId\":\"doing-stageId\",\"stageId\":\"undoing-stageId2\",\"creationTime\":\"2017-02-15 11:57:35\"}"), Activity.class);
        List<Activity> activities = Arrays.asList(moveInActivity, moveOutActivity);
        when(activityService.isMoveInProcess(any())).thenReturn(true).thenReturn(false);
        double actualElapsedDays = statisticsService.calculateElapsedDays(activities);

        assertEquals(expectedElapsedDays, actualElapsedDays);
    }

    @Test
    public void shouldReturn1_5Days() {
        double expectedElapsedDays = 1.5;
        Activity moveInActivity1 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo1\",\"cardId\":\"foo-cardId\",\"prevStageId\":\"undoing-stageId1\",\"stageId\":\"doing-stageId1\",\"creationTime\":\"2017-02-15 09:57:35\"}"), Activity.class);
        Activity moveOutActivity1 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo2\",\"cardId\":\"foo-cardId2\",\"prevStageId\":\"doing-stageId1\",\"stageId\":\"undoing-stageId1\",\"creationTime\":\"2017-02-15 11:57:35\"}"), Activity.class);
        Activity moveInActivity2 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo3\",\"cardId\":\"foo-cardId3\",\"prevStageId\":\"undoing-stageId2\",\"stageId\":\"doing-stageId2\",\"creationTime\":\"2017-02-15 14:57:35\"}"), Activity.class);
        Activity moveOutActivity2 = JSON.toJavaObject(JSON.parseObject("{\"id\":\"foo4\",\"cardId\":\"foo-cardId4\",\"prevStageId\":\"doing-stageId2\",\"stageId\":\"undoing-stageId2\",\"creationTime\":\"2017-02-16 13:57:35\"}"), Activity.class);
        List<Activity> activities = Arrays.asList(moveInActivity1, moveOutActivity1, moveInActivity2, moveOutActivity2);
        when(activityService.isMoveInProcess(any())).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        double actualElapsedDays = statisticsService.calculateElapsedDays(activities);

        assertEquals(expectedElapsedDays, actualElapsedDays);
    }
}
