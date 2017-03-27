package org.thiki.kanban.statistics.burndownchart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.statistics.burnDownChart.BurnDownChartService;

import javax.annotation.Resource;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by winie on 2017/3/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BurnDownChartServiceTest extends TestBase {

    @Resource
    @InjectMocks
    private BurnDownChartService burnDownChartService;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author,sort_number) VALUES ('boardId','this is the first stage.','someone',1)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('stageId','this is the first stage.','someone','boardId')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,deadline,author,stage_id,size) VALUES ('fooId','this is the card summary.','play badminton','2017-02-13',1,'stageId',3)");
        jdbcTemplate.execute("INSERT INTO  kb_sprint (id,sprint_name,start_time,end_time,board_id,status) VALUES ('fooId', 'sprint_name','2017-3-26 2:11:10', '2017-3-28 2:11:10', 'boardId', 1)");
    }
    @Test
    public void should_succeed_if_analyse_By_SprintId() {
        boolean is_true=burnDownChartService.findSprintBySprintId("fooId");
        assertEquals(is_true, true);
    }

    @Test
    public void should_succeed_if_analyse() {
        boolean is_true=burnDownChartService.analyse("fooId","boardId");
        assertEquals(is_true, true);
    }

    @Test
    public void should_succeed_if_analyse_By_All() {
        burnDownChartService.findAllSprint("20170327");
        assertTrue(jdbcTemplate.queryForList("SELECT * FROM kb_burn_down_chart where sprint_id='fooId'").size()>0);
    }
    @Test
    public void findBySprintIdAndBoardId_shouldReturnBurnDownChartSuccessfully(){
        burnDownChartService.analyse("fooId","boardId");
        given().header("userName", userName)
                .when()
                .get("/statistics/projects/1/boards/boardId/sprint/fooId")
                .then()
                .statusCode(200)
                .body("burnDownCharts[0].storyPoint", equalTo(3))
                .body("_links.board.href", endsWith("/boards/boardId"));
    }
}
