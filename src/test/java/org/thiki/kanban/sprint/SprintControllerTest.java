package org.thiki.kanban.sprint;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.foundation.common.date.DateService;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;

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
                .body("{\"startTime\":\"2017-02-04 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/board-fooId/sprints")
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
                .body("{\"startTime\":\"2017-02-06 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/board-fooId/sprints")
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
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/board-fooId/sprints")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.UNARCHIVE_SPRINT_EXIST.message()))
                .body("code", equalTo(SprintCodes.UNARCHIVE_SPRINT_EXIST.code()));
    }

    @Scenario("更新迭代信息")
    @Test
    public void updateSprint() {
        dbPreparation.table("kb_sprint").names("id,board_id,status").values("fooId", "board-fooId", 1).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/board-fooId/sprints/fooId")
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
                .body("{\"startTime\":\"2017-02-06 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/board-fooId/sprints/fooId")
                .then()
                .statusCode(400)
                .body("message", equalTo(SprintCodes.START_TIME_IS_AFTER_END_TIME.message()))
                .body("code", equalTo(SprintCodes.START_TIME_IS_AFTER_END_TIME.code()));
    }

    @Scenario("更新一个迭代时，如果迭代不存在,则不允许操作")
    @Test
    public void notAllowedIfSprintDoesNotExistWhenUpdating() {
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/board-fooId/sprints/fooId")
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
                .get("/boards/board-fooId/sprints/activeSprint")
                .then()
                .body("id", equalTo("fooId"))
                .body("startTime", equalTo("2017-02-04 12:11:44.000000"))
                .body("endTime", equalTo("2017-02-05 12:11:44.000000"))
                .body("status", equalTo(SprintCodes.SPRINT_IN_PROGRESS))
                .body("creationTime", notNullValue())
                .body("_links.board.href", endsWith("/boards/board-fooId"))
                .body("_links.self.href", endsWith("/boards/board-fooId/sprints/fooId"));
    }

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
                .get("/boards/board-fooId/sprints/activeSprint")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo(SprintCodes.ACTIVE_SPRINT_IS_NOT_FOUND.message()))
                .body("code", equalTo(SprintCodes.ACTIVE_SPRINT_IS_NOT_FOUND.code()));
    }

    @Scenario("完成迭代")
    @Test
    public void completeSprint() {
        dbPreparation.table("kb_sprint").names("id,board_id,status").values("fooId", "board-fooId", 1).exec();
        given().header("userName", "someone")
                .body("{\"startTime\":\"2017-02-03 12:11:44\",\"endTime\":\"2017-02-05 12:11:44\",\"status\":\"2\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/board-fooId/sprints/fooId")
                .then()
                .statusCode(200)
                .body("status", equalTo(SprintCodes.SPRINT_COMPLETED))
                .body("_links.board.href", endsWith("/boards/board-fooId"))
                .body("_links.self.href", endsWith("/boards/board-fooId/sprints/fooId"));
    }
}
