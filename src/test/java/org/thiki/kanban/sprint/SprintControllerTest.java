package org.thiki.kanban.sprint;

import com.jayway.restassured.http.ContentType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.stage.StageCodes;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 02/04/17.
 */
@Domain(order = DomainOrder.SPRINT, name = "迭代")
@RunWith(SpringJUnit4ClassRunner.class)
public class SprintControllerTest extends TestBase {

    @Scenario("当创建一个迭代时,如果参数合法,则创建成功并返回创建后的迭代信息")
    @Test
    public void shouldReturn201WhenCreateSprintSuccessfully() {
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-04 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/projects/project-fooId/boards/board-fooId/sprints")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("startTime", equalTo("2017-02-04 12:11:44.000000"))
                .body("endTime", equalTo("2017-02-05 12:11:44.000000"))
                .body("status", equalTo(SprintCodes.SPRINT_IN_PROGRESS))
                .body("creationTime", notNullValue())
                .body("_links.board.href", endsWith("/boards/board-fooId"))
                .body("_links.self.href", endsWith("/boards/board-fooId/sprints/fooId"));
    }

    @Scenario("当创建一个迭代时,如果开始日期晚于结束日期,则不允许创建")
    @Test
    public void notAllowedIfStartTimeAfterEndTime() {
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-06 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/projects/project-fooId/boards/board-fooId/sprints")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.START_TIME_IS_AFTER_END_TIME.message()))
                .body("code", equalTo(SprintCodes.START_TIME_IS_AFTER_END_TIME.code()));
    }

    @Scenario("当创建一个迭代时,如果存在尚未归档的迭代,则不允许创建")
    @Test
    public void notAllowedIfUnArchivedSprintExist() {
        dbPreparation.table("kb_sprint").names("id,board_id,status").values("fooId", "board-fooId", 1).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/projects/project-fooId/boards/board-fooId/sprints")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.UNARCHIVE_SPRINT_EXIST.message()))
                .body("code", equalTo(SprintCodes.UNARCHIVE_SPRINT_EXIST.code()));
    }

    @Scenario("当创建一个迭代时,如果在当前看板下已经存在相同名称的迭代,则不允许创建")
    @Test
    public void notAllowedIfSprintNameAlreadyExist() {
        dbPreparation.table("kb_sprint").names("id,board_id,sprint_name,status").values("fooId", "board-fooId", "sprintName-foo", 2).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/projects/project-fooId/boards/board-fooId/sprints")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.SPRINT_NAME_ALREADY_EXISTS.message()))
                .body("code", equalTo(SprintCodes.SPRINT_NAME_ALREADY_EXISTS.code()));
    }

    @Scenario("更新迭代信息")
    @Test
    public void updateSprint() {
        dbPreparation.table("kb_sprint").names("id,board_id,status").values("fooId", "board-fooId", 1).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/project-fooId/boards/board-fooId/sprints/fooId")
                .then()
                .statusCode(200)
                .body("startTime", equalTo("2017-02-03 12:11:44.000000"))
                .body("endTime", equalTo("2017-02-05 12:11:44.000000"))
                .body("_links.board.href", endsWith("/boards/board-fooId"))
                .body("_links.self.href", endsWith("/boards/board-fooId/sprints/fooId"));
    }

    @Scenario("更新一个迭代时，如果开始日期晚于结束日期，不允许更新")
    @Test
    public void notAllowedIfStartTimeAfterEndTimeWhenUpdating() {
        dbPreparation.table("kb_sprint").names("id,board_id,status").values("fooId", "board-fooId", 1).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-06 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/project-fooId/boards/board-fooId/sprints/fooId")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.START_TIME_IS_AFTER_END_TIME.message()))
                .body("code", equalTo(SprintCodes.START_TIME_IS_AFTER_END_TIME.code()));
    }

    @Scenario("更新一个迭代时，如果在当前看板下已经存在相同名称的迭代，不允许更新")
    @Test
    public void notAllowedIfSprintNameAlreadyExistWhenUpdating() {
        dbPreparation.table("kb_sprint").names("id,board_id,sprint_name,status").values("fooId-other", "board-fooId", "sprintName-foo", 2).exec();
        dbPreparation.table("kb_sprint").names("id,board_id,status").values("fooId", "board-fooId", 1).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-01 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/project-fooId/boards/board-fooId/sprints/fooId")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.SPRINT_NAME_ALREADY_EXISTS.message()))
                .body("code", equalTo(SprintCodes.SPRINT_NAME_ALREADY_EXISTS.code()));
    }

    @Scenario("更新一个迭代时，如果迭代不存在,则不允许操作")
    @Test
    public void notAllowedIfSprintDoesNotExistWhenUpdating() {
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/project-fooId/boards/board-fooId/sprints/fooId")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.SPRINT_IS_NOT_EXIST.message()))
                .body("code", equalTo(SprintCodes.SPRINT_IS_NOT_EXIST.code()));
    }

    @Scenario("获取看板的当前迭代")
    @Test
    public void loadActiveSprint() {
        dbPreparation.table("kb_sprint").names("id,start_time,end_time,board_id,status").values("fooId", "2017-02-04 12:11:44", "2017-02-05 12:11:44", "board-fooId", 1).exec();
        given().header("userName", "someone")
                .when()
                .get("/projects/project-fooId/boards/board-fooId/sprints/activeSprint")
                .then()
                .body("id", equalTo("fooId"))
                .body("startTime", equalTo("2017-02-04 12:11:44.000000"))
                .body("endTime", equalTo("2017-02-05 12:11:44.000000"))
                .body("status", equalTo(SprintCodes.SPRINT_IN_PROGRESS))
                .body("creationTime", notNullValue())
                .body("_links.board.href", endsWith("/boards/board-fooId"))
                .body("_links.self.href", endsWith("/boards/board-fooId/sprints/fooId"));
    }

    @Ignore
    @Scenario("获取看板的当前迭代时，计算出相应的天数")
    @Test
    public void calculateRemainDaysWhenLoadingActiveSprint() {
        int wentDays = 2;
        int timeBox = 15;
        String currentTime = DateService.instance().getNow_EN();
        String startTime = DateService.instance().addDay(currentTime, -wentDays);
        String endTime = DateService.instance().addDay(startTime, timeBox);

        dbPreparation.table("kb_sprint").names("id,start_time,end_time,board_id,status").values("fooId", startTime, endTime, "board-fooId", 1).exec();
        given().header("userName", "someone")
                .when()
                .get("/boards/board-fooId/sprints/activeSprint")
                .then()
                .body("status", equalTo(SprintCodes.SPRINT_IN_PROGRESS))
                .body("remainingDays", equalTo(timeBox - wentDays - 1))
                .body("totalDays", equalTo(timeBox))
                .body("wentDays", equalTo(wentDays));
    }

    @Scenario("获取看板的当前迭代时，如果不存在激活的迭代，则告知客户端错误")
    @Test
    public void should_throw_404_exception_if_active_sprint_is_not_exist() {
        given().header("userName", "someone")
                .when()
                .get("/projects/project-fooId/boards/board-fooId/sprints/activeSprint")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo(SprintCodes.ACTIVE_SPRINT_IS_NOT_FOUND.message()))
                .body("code", equalTo(SprintCodes.ACTIVE_SPRINT_IS_NOT_FOUND.code()));
    }

    @Scenario("迭代完成时，如果当前迭代已经处于归档状态，则不允许操作")
    @Test
    public void notAllowedIfSprintWasAlreadyArchivedWhenCompletingSprint() {
        dbPreparation.table("kb_sprint").names("id,start_time,end_time,board_id,status").values("fooId", "2017-02-04 12:11:44", "2017-02-05 12:11:44", "board-fooId", 2).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"status\":\"2\",\"sprintName\":\"sprintName-foo\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/project-fooId/boards/board-fooId/sprints/fooId")
                .then()
                .body("code", equalTo(SprintCodes.SPRINT_ALREADY_ARCHIVED.code()))
                .body("message", equalTo(SprintCodes.SPRINT_ALREADY_ARCHIVED.message()));
    }

    @Scenario("迭代归档->迭代完成时将现有的完成列中的卡片进行归档")
    @Test
    public void shouldReturn201WhenArchiveSuccessfully() {
        dbPreparation.table("kb_stage")
                .names("id,title,author,board_id,type,status")
                .values("stage_fooId", "title", "someone", "board-fooId", StageCodes.STAGE_TYPE_IN_PLAN, StageCodes.STAGE_STATUS_DONE)
                .exec();

        dbPreparation.table("kb_sprint")
                .names("id,sprint_name,start_time,end_time,board_id,status")
                .values("fooId", "sprint_name", "2017-02-04 12:11:44", "2017-02-05 12:11:44", "board-fooId", 1)
                .exec();

        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('card-fooId','this is the card summary.','play badminton','someone','stage_fooId')");

        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"sprintName\":\"sprintName-foo\",\"status\":\"2\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/project-fooId/boards/board-fooId/sprints/fooId")
                .then()
                .statusCode(200)
                .body("status", equalTo(SprintCodes.SPRINT_COMPLETED))
                .body("competedTime", notNullValue())
                .body("archiveId", equalTo("fooId"))
                .body("_links.board.href", endsWith("/boards/board-fooId"))
                .body("_links.self.href", endsWith("/boards/board-fooId/sprints/fooId"));

        assertEquals("sprintName-foo归档", jdbcTemplate.queryForObject("select title FROM kb_stage WHERE status=9 AND type=9", String.class));
        assertEquals(1, jdbcTemplate.queryForList("select count(*) FROM kb_card WHERE stage_id='stage_fooId'").size());
    }
}
