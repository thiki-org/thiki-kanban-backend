package org.thiki.kanban.stage;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.board.BoardCodes;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 5/14/16.
 */
@Domain(order = DomainOrder.STAGE, name = "环节")
@RunWith(SpringJUnit4ClassRunner.class)
public class StagesControllerTest extends TestBase {

    @Scenario("创建一个新的stage后,返回自身及links信息")
    @Test
    public void shouldReturn201WhenCreateStageSuccessfully() {
        given().header("userName", userName)
                .body("{\"title\":\"this is the stage title.\",\"description\":\"description.\",\"wipLimit\":\"20\",\"limitationOnEntry\":\"准入哦.\",\"definitionOfDone\":\"完成了哦.\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/stages")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the stage title."))
                .body("description", equalTo("description."))
                .body("author", equalTo(userName))
                .body("creationTime", notNullValue())
                .body("wipLimit", equalTo(20))
                .body("_links.all.href", endsWith("/boards/feeId/stages"))
                .body("_links.cards.href", endsWith("/boards/feeId/cards"))
                .body("_links.self.href", endsWith("/boards/feeId/stages/fooId"));
    }


    @Scenario("创建新的stage时,如果名称为空,则不允许创建并返回客户端400错误")
    @Test
    public void shouldFailedIfStageTitleIsEmpty() {
        given().header("userName", userName)
                .body("{\"title\":\"\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/stages")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(StageCodes.titleIsRequired));
    }

    @Scenario("创建新的stage时,如果名称为空字符串,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenStageTitleIsEmpty() {
        given().header("userName", userName)
                .body("{\"boardId\":\"feeId\"}}")
                .body("{\"title\":\"\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/stages")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(StageCodes.titleIsRequired));
    }

    @Scenario("创建新的stage时,如果名称长度超限,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenStageTitleIsTooLong() {
        given().header("userName", userName)
                .body("{\"title\":\"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/stages")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(StageCodes.titleIsInvalid));
    }

    @Scenario("创建新的stage时,同一看板下已经存在同名,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenStageTitleIsAlreadyExits() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','stage',1,'feeId')");
        given().header("userName", userName)
                .body("{\"title\":\"stage\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/stages")
                .then()
                .statusCode(400)
                .body("code", equalTo(StageCodes.TITLE_IS_ALREADY_EXISTS.code()))
                .body("message", equalTo(StageCodes.TITLE_IS_ALREADY_EXISTS.message()));
    }

    @Scenario("当根据stageId查找stage时,如果stage存在,则将其返回")
    @Test
    public void shouldReturnStageWhenFindStageById() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.',1,'feeId')");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/stages/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("title", equalTo("this is the first stage."))
                .body("_links.all.href", endsWith("/boards/feeId/stages"))
                .body("_links.self.href", endsWith("/boards/feeId/stages/fooId"));
    }

    @Scenario("更新stage时,如果参数合法且待更新的stage存在,则更新成功")
    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.',1,'feeId')");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"status\":\"1\"}")
                .when()
                .put("/boards/feeId/stages/fooId")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("status", equalTo(1))
                .body("_links.all.href", endsWith("/boards/feeId/stages"))
                .body("_links.self.href", endsWith("/boards/feeId/stages/fooId"));
        assertEquals("newTitle", jdbcTemplate.queryForObject("select title from kb_stage where id='fooId'", String.class));
    }

    @Scenario("更新stage时,修改在制品数量")
    @Test
    public void shouldUpdateWipSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.',1,'feeId')");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"this is the first stage.\",\"wipLimit\":\"20\"}")
                .when()
                .put("/boards/feeId/stages/fooId")
                .then()
                .statusCode(200)
                .body("wipLimit", equalTo(20))
                .body("_links.all.href", endsWith("/boards/feeId/stages"))
                .body("_links.self.href", endsWith("/boards/feeId/stages/fooId"));
    }

    @Scenario("更新stage时,修改准入限制")
    @Test
    public void shouldUpdateLimitationOnEntrySuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.',1,'feeId')");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"this is the first stage.\",\"limitationOnEntry\":\"我定义好准入规则了哦.\"}")
                .when()
                .put("/boards/feeId/stages/fooId")
                .then()
                .statusCode(200)
                .body("limitationOnEntry", equalTo("我定义好准入规则了哦."))
                .body("_links.all.href", endsWith("/boards/feeId/stages"))
                .body("_links.self.href", endsWith("/boards/feeId/stages/fooId"));
    }

    @Scenario("更新stage时,修改完成规则")
    @Test
    public void shouldUpdateDefinitionOfDoneSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.',1,'feeId')");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"this is the first stage.\",\"definitionOfDone\":\"I'm definition done rule.\"}")
                .when()
                .put("/boards/feeId/stages/fooId")
                .then()
                .statusCode(200)
                .body("definitionOfDone", equalTo("I'm definition done rule."))
                .body("_links.all.href", endsWith("/boards/feeId/stages"))
                .body("_links.self.href", endsWith("/boards/feeId/stages/fooId"));
    }

    @Scenario("更新stage时,如果参数合法但待更新的stage不存在,则更新失败")
    @Test
    public void update_shouldFailedWhenTheStageToUpdateIsNotExists() {
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/stages/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(StageCodes.STAGE_IS_NOT_EXIST.code()))
                .body("message", equalTo(StageCodes.STAGE_IS_NOT_EXIST.message()));
    }

    @Scenario("设置环节状态属性->在将环节设置为完成环节时,如果当前环节非迭代中的环节,则不允许设置")
    @Test
    public void shouldFailedWhenTheDoneStageIsNotInSprint() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId',' ','someone','board-feeId',0,1)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"status\":\"9\"}")
                .when()
                .put("/boards/feeId/stages/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(StageCodes.STAGE_TYPE_IS_NOT_IN_SPRINT.code()))
                .body("message", equalTo(StageCodes.STAGE_TYPE_IS_NOT_IN_SPRINT.message()));
    }

    @Scenario("设置环节状态属性,如果目标环节是迭代环节,且已经存在完成列，则不允许再设置")
    @Test
    public void shouldFailedWhenTheDoneStageIsAlreadyExist() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type) VALUES ('fooId',' ','someone','board-feeId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId-other',' ','someone','board-feeId',1,9)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"status\":\"9\"}")
                .when()
                .put("/boards/board-feeId/stages/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(StageCodes.DONE_STAGE_IS_ALREADY_EXIST.code()))
                .body("message", equalTo(StageCodes.DONE_STAGE_IS_ALREADY_EXIST.message()));
    }

    @Scenario("设置环节类别,不允许直接将环节设置为已经归档")
    @Test
    public void shouldFailedWhenSettingStageToArchiveDirectly() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type) VALUES ('fooId',' ','someone','board-feeId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId-other',' ','someone','board-feeId',1,9)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"type\":\"9\"}")
                .when()
                .put("/boards/board-feeId/stages/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(StageCodes.NOT_ALLOW_SET_STAGE_TO_ARCHIVE_DIRECTLY.code()))
                .body("message", equalTo(StageCodes.NOT_ALLOW_SET_STAGE_TO_ARCHIVE_DIRECTLY.message()));
    }

    @Scenario("重新排序>用户创建环节后,可以调整其顺序")
    @Test
    public void resortStage() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,sort_number,type) VALUES ('stage-fooId1','stageTitle1',1,'feeId',0,1)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,sort_number,type) VALUES ('stage-fooId2','stageTitle2',1,'feeId',1,1)");

        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("[{\"id\":\"stage-fooId1\",\"sortNumber\":2},{\"id\":\"stage-fooId2\",\"sortNumber\":3}]")
                .when()
                .put("/boards/feeId/stages/movement")
                .then()
                .statusCode(200)
                .body("stages[0].title", equalTo("stageTitle1"))
                .body("stages[0].sortNumber", equalTo(2))
                .body("stages[1].title", equalTo("stageTitle2"))
                .body("stages[1].sortNumber", equalTo(3))
                .body("_links.self.href", endsWith("/boards/feeId/stages/movement"));
    }

    @Scenario("当删除一个stage时,如果待删除的stage存在,则删除成功")
    @Test
    public void shouldDeleteSuccessfullyWhenTheStageIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.',1,'feeId')");
        given().header("userName", userName)
                .when()
                .delete("/boards/feeId/stages/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_stage WHERE  delete_status=1").size());
    }

    @Scenario("当删除一个stage时,如果待删除的stage不存在,则删除成功并返回客户端错误")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenStageToDeleteIsNotExist() throws Exception {
        given().header("userName", userName)
                .when()
                .delete("/boards/feeId/stages/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(StageCodes.STAGE_IS_NOT_EXIST.code()))
                .body("message", equalTo(StageCodes.STAGE_IS_NOT_EXIST.message()));
    }

    @Scenario("通过boardId获取所有的stage")
    @Test
    public void shouldReturnAllEntriesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId','this is the first stage.','tao','feeId',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('randomId','this is the first stage.','tao','feeId2')");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/stages")
                .then()
                .statusCode(200)
                .body("stages[0].title", equalTo("this is the first stage."))
                .body("stages[0].author", equalTo("tao"))
                .body("stages[0].creationTime", notNullValue())
                .body("stages[0]._links.all.href", endsWith("/boards/feeId/stages"))
                .body("stages[0]._links.self.href", endsWith("/boards/feeId/stages/fooId"))
                .body("stages[0]._links.cards.href", endsWith("/boards/feeId/cards"))
                .body("_links.self.href", endsWith("/boards/feeId/stages"))
                .body("_links.movement.href", endsWith("/boards/feeId/stages/movement"));
    }

    @Scenario("通过指定状态的stage-默认为迭代视图")
    @Test
    public void shouldReturnDefaultSprintStagesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId1','this is the first stage.','tao','feeId',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId2','this is the first stage.','tao','feeId',3,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId3','this is the first stage.','tao','feeId',1,9)");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/stages")
                .then()
                .statusCode(200)
                .body("stages.size()", equalTo(2));
    }

    @Scenario("通过指定状态的stage-迭代视图")
    @Test
    public void shouldReturnSprintStagesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId1','this is the first stage.','tao','feeId',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId2','this is the first stage.','tao','feeId',3,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId3','this is the first stage.','tao','feeId',1,9)");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/stages?viewType=" + BoardCodes.VIEW_TYPE_SPRINT)
                .then()
                .statusCode(200)
                .body("stages.size()", equalTo(2));
    }


    @Scenario("通过指定状态的stage-全景视图")
    @Test
    public void shouldReturnAllStagesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId1','this is the first stage.','tao','feeId',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId2','this is the first stage.','tao','feeId',3,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId3','this is the first stage.','tao','feeId',1,9)");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/stages?viewType=" + BoardCodes.VIEW_TYPE_FULL_VIEW)
                .then()
                .statusCode(200)
                .body("stages.size()", equalTo(3));
    }

    @Scenario("通过指定状态的stage-归档视图")
    @Test
    public void shouldReturnArchivedStagesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId1','this is the first stage.','tao','feeId',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId2','this is the first stage.','tao','feeId',3,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId3','this is the first stage.','tao','feeId',9,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId4','this is the first stage.','tao','feeId',9,9)");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/stages?viewType=" + BoardCodes.VIEW_TYPE_ARCHIVE)
                .then()
                .statusCode(200)
                .body("stages.size()", equalTo(2));
    }


    @Scenario("通过指定状态的stage-产品路线视图")
    @Test
    public void shouldReturnRoadMapStagesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId1','this is the first stage.','tao','feeId',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId2','this is the first stage.','tao','feeId',1,1)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId3','this is the first stage.','tao','feeId',9,0)");
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('fooId4','this is the first stage.','tao','feeId',9,0)");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/stages?viewType=" + BoardCodes.VIEW_TYPE_ROAD_MAP)
                .then()
                .statusCode(200)
                .body("stages.size()", equalTo(3));
    }
}
