package org.thiki.kanban.procedure;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
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
@Domain(order = DomainOrder.PROCEDURE, name = "工序")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProceduresControllerTest extends TestBase {

    @Scenario("创建一个新的procedure后,返回自身及links信息")
    @Test
    public void shouldReturn201WhenCreateProcedureSuccessfully() {
        given().header("userName", userName)
                .body("{\"title\":\"this is the procedure title.\",\"description\":\"description.\",\"wipNum\":\"20\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the procedure title."))
                .body("description", equalTo("description."))
                .body("author", equalTo(userName))
                .body("creationTime", notNullValue())
                .body("wipNum", equalTo(20))
                .body("_links.all.href", endsWith("/boards/feeId/procedures"))
                .body("_links.cards.href", endsWith("/boards/feeId/procedures/fooId/cards"))
                .body("_links.self.href", endsWith("/boards/feeId/procedures/fooId"));
    }


    @Scenario("创建新的procedure时,如果名称为空,则不允许创建并返回客户端400错误")
    @Test
    public void shouldFailedIfProcedureTitleIsEmpty() {
        given().header("userName", userName)
                .body("{\"title\":\"\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(ProcedureCodes.titleIsRequired));
    }

    @Scenario("创建新的procedure时,如果名称为空字符串,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenProcedureTitleIsEmpty() {
        given().header("userName", userName)
                .body("{\"boardId\":\"feeId\"}}")
                .body("{\"title\":\"\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(ProcedureCodes.titleIsRequired));
    }

    @Scenario("创建新的procedure时,如果名称长度超限,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenProcedureTitleIsTooLong() {
        given().header("userName", userName)
                .body("{\"title\":\"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(ProcedureCodes.titleIsInvalid));
    }

    @Scenario("创建新的procedure时,同一看板下已经存在同名,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenProcedureTitleIsAlreadyExits() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id) VALUES ('fooId','procedure',1,'feeId')");
        given().header("userName", userName)
                .body("{\"title\":\"procedure\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProcedureCodes.TITLE_IS_ALREADY_EXISTS.code()))
                .body("message", equalTo(ProcedureCodes.TITLE_IS_ALREADY_EXISTS.message()));
    }

    @Scenario("当根据procedureId查找procedure时,如果procedure存在,则将其返回")
    @Test
    public void shouldReturnProcedureWhenFindProcedureById() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("title", equalTo("this is the first procedure."))
                .body("_links.all.href", endsWith("/boards/feeId/procedures"))
                .body("_links.self.href", endsWith("/boards/feeId/procedures/fooId"));
    }

    @Scenario("更新procedure时,如果参数合法且待更新的procedure存在,则更新成功")
    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"status\":\"1\"}")
                .when()
                .put("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("status", equalTo(1))
                .body("_links.all.href", endsWith("/boards/feeId/procedures"))
                .body("_links.self.href", endsWith("/boards/feeId/procedures/fooId"));
        assertEquals("newTitle", jdbcTemplate.queryForObject("select title from kb_procedure where id='fooId'", String.class));
    }
    @Scenario("更新procedure时,修改在制品数量")
    @Test
    public void shouldUpdateWipSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"this is the first procedure.\",\"wipNum\":\"20\"}")
                .when()
                .put("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(200)
                .body("wipNum", equalTo(20))
                .body("_links.all.href", endsWith("/boards/feeId/procedures"))
                .body("_links.self.href", endsWith("/boards/feeId/procedures/fooId"));
    }

    @Scenario("更新procedure时,如果参数合法但待更新的procedure不存在,则更新失败")
    @Test
    public void update_shouldFailedWhenTheProcedureToUpdateIsNotExists() {
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProcedureCodes.PROCEDURE_IS_NOT_EXIST.code()))
                .body("message", equalTo(ProcedureCodes.PROCEDURE_IS_NOT_EXIST.message()));
    }

    @Scenario("设置工序状态属性->在将工序设置为完成工序时,如果当前工序非迭代中的工序,则不允许设置")
    @Test
    public void shouldFailedWhenTheDoneProcedureIsNotInSprint() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,type,status) VALUES ('fooId',' ','someone','board-feeId',0,1)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"status\":\"9\"}")
                .when()
                .put("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProcedureCodes.PROCEDURE_TYPE_IS_NOT_IN_SPRINT.code()))
                .body("message", equalTo(ProcedureCodes.PROCEDURE_TYPE_IS_NOT_IN_SPRINT.message()));
    }

    @Scenario("设置工序状态属性,如果目标工序是迭代工序,且已经存在完成列，则不允许再设置")
    @Test
    public void shouldFailedWhenTheDoneProcedureIsAlreadyExist() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,type) VALUES ('fooId',' ','someone','board-feeId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,type,status) VALUES ('fooId-other',' ','someone','board-feeId',1,9)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"status\":\"9\"}")
                .when()
                .put("/boards/board-feeId/procedures/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProcedureCodes.DONE_PROCEDURE_IS_ALREADY_EXIST.code()))
                .body("message", equalTo(ProcedureCodes.DONE_PROCEDURE_IS_ALREADY_EXIST.message()));
    }

    @Scenario("设置工序类别,不允许直接将工序设置为已经归档")
    @Test
    public void shouldFailedWhenSettingProcedureToArchiveDirectly() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,type) VALUES ('fooId',' ','someone','board-feeId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,type,status) VALUES ('fooId-other',' ','someone','board-feeId',1,9)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"orderNumber\":\"0\",\"type\":\"9\"}")
                .when()
                .put("/boards/board-feeId/procedures/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProcedureCodes.NOT_ALLOW_SET_PROCEDURE_TO_ARCHIVE_DIRECTLY.code()))
                .body("message", equalTo(ProcedureCodes.NOT_ALLOW_SET_PROCEDURE_TO_ARCHIVE_DIRECTLY.message()));
    }

    @Scenario("重新排序>用户创建工序后,可以调整其顺序")
    @Test
    public void resortProcedure() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,sort_number) VALUES ('procedure-fooId1','procedureTitle1',1,'feeId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,sort_number) VALUES ('procedure-fooId2','procedureTitle2',1,'feeId',1)");

        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("[{\"id\":\"procedure-fooId1\",\"sortNumber\":2},{\"id\":\"procedure-fooId2\",\"sortNumber\":3}]")
                .when()
                .put("/boards/feeId/procedures/sortNumbers")
                .then()
                .statusCode(200)
                .body("procedures[0].title", equalTo("procedureTitle1"))
                .body("procedures[0].sortNumber", equalTo(2))
                .body("procedures[1].title", equalTo("procedureTitle2"))
                .body("procedures[1].sortNumber", equalTo(3))
                .body("_links.self.href", endsWith("/boards/feeId/procedures/sortNumbers"));
    }

    @Scenario("当删除一个procedure时,如果待删除的procedure存在,则删除成功")
    @Test
    public void shouldDeleteSuccessfullyWhenTheProcedureIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
        given().header("userName", userName)
                .when()
                .delete("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_procedure WHERE  delete_status=1").size());
    }

    @Scenario("当删除一个procedure时,如果待删除的procedure不存在,则删除成功并返回客户端错误")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenProcedureToDeleteIsNotExist() throws Exception {
        given().header("userName", userName)
                .when()
                .delete("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProcedureCodes.PROCEDURE_IS_NOT_EXIST.code()))
                .body("message", equalTo(ProcedureCodes.PROCEDURE_IS_NOT_EXIST.message()));
    }

    @Scenario("通过boardId获取所有的procedure")
    @Test
    public void shouldReturnAllEntriesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id,type,status) VALUES ('fooId','this is the first procedure.','tao','feeId',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id) VALUES ('randomId','this is the first procedure.','tao','feeId2')");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/procedures")
                .then()
                .statusCode(200)
                .body("procedures[0].title", equalTo("this is the first procedure."))
                .body("procedures[0].author", equalTo("tao"))
                .body("procedures[0].creationTime", notNullValue())
                .body("procedures[0]._links.all.href", endsWith("/boards/feeId/procedures"))
                .body("procedures[0]._links.self.href", endsWith("/boards/feeId/procedures/fooId"))
                .body("procedures[0]._links.cards.href", endsWith("/boards/feeId/procedures/fooId/cards"))
                .body("_links.self.href", endsWith("/boards/feeId/procedures"))
                .body("_links.sortNumbers.href", endsWith("/boards/feeId/procedures/sortNumbers"));
    }
}
