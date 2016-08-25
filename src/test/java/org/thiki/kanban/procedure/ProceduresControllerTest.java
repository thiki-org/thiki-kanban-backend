package org.thiki.kanban.procedure;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 5/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProceduresControllerTest extends TestBase {

    private String userName = "fooName";

    @Scenario("创建一个新的procedure后,返回自身及links信息")
    @Test
    public void shouldReturn201WhenCreateProcedureSuccessfully() {
        given().header("userName", userName)
                .body("{\"title\":\"this is the procedure title.\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the procedure title."))
                .body("reporter", equalTo(userName))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/fooId/cards"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/procedures/fooId"));
    }

    @Scenario("创建一个新的procedure,如果它并不是指定boardId下第一个procedure,则其排序号应根据当前procedure数量自动增加")
    @Test
    public void create_orderNumberShouldAutoIncrease() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id) VALUES ('existedFooId','this is the first procedure.',1,'feeId')");
        given().header("userName", userName)
                .body("{\"title\":\"this is the procedure title.\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the procedure title."))
                .body("reporter", equalTo(11222))
                .body("creationTime", notNullValue())
                .body("orderNumber", equalTo(1))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/fooId/cards"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/procedures/fooId"));
    }

    @Scenario("创建新的procedure时,如果名称为空,则不允许创建并返回客户端400错误")
    @Test
    public void shouldFailedIfProcedureTitleIsEmpty() {
        given().header("userName", userName)
                .body("{\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(ProcedureCodes.titleIsRequired));
    }

    @Scenario("创建新的procedure时,如果boardID为空,则不允许创建并返回客户端400错误")
    @Test
    public void shouldFailedIfProcedureBoardIdIsEmpty() {
        given().header("userName", userName)
                .body("{\"title\":\"this is the procedure title.\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("boardId不能为空"));
    }

    @Scenario("创建新的procedure时,如果名称为空字符串,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenProcedureTitleIsEmpty() {
        given().header("userName", userName)
                .body("{\"boardId\":\"feeId\"}}")
                .body("{\"title\":\"\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(ProcedureCodes.titleIsInvalid));
    }

    @Scenario("创建新的procedure时,如果名称长度超限,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenProcedureTitleIsTooLong() {
        given().header("userName", userName)
                .body("{\"boardId\":\"feeId\"}}")
                .body("{\"title\":\"长度超限长度超限长度超限\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(ProcedureCodes.titleIsInvalid));
    }


    @Scenario("当根据procedureId查找procedure时,如果procedure存在,则将其返回")
    @Test
    public void shouldReturnProcedureWhenFindProcedureById() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("title", equalTo("this is the first procedure."))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/procedures/fooId"));
    }

    @Scenario("更新procedure时,如果参数合法且待更新的procedure存在,则更新成功")
    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/procedures/fooId"));
        assertEquals("newTitle", jdbcTemplate.queryForObject("select title from kb_procedure where id='fooId'", String.class));
    }

    @Scenario("更新procedure时,如果参数合法但待更新的procedure不存在,则更新失败")
    @Test
    public void update_shouldFailedWhenTheProcedureToUpdateIsNotExists() {
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/procedures/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("procedure[fooId] is not found."));
    }

    @Scenario("当移动一个procedure时,移动后的排序小于其原先的排序")
    @Test
    public void update_shouldResortSuccessfullyWhenCurrentSortNumberIsLessThanOriginNumber() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id,order_number) VALUES ('fooId1','this is the first procedure.',1,'feeId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id,order_number) VALUES ('fooId2','this is the first procedure.',1,'feeId',1)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}}")
                .when()
                .put("/boards/feeId/procedures/fooId2")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/procedures/fooId2"));
        assertEquals("1", jdbcTemplate.queryForObject("select order_number from kb_procedure where id='fooId1'", String.class));
        assertEquals("0", jdbcTemplate.queryForObject("select order_number from kb_procedure where id='fooId2'", String.class));
    }

    @Scenario("当移动一个procedure时,移动后的排序大于其原先的排序")
    @Test
    public void update_shouldResortSuccessfullyWhenCurrentSortNumberIsMoreThanOriginNumber() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id,order_number) VALUES ('fooId1','this is the first procedure.',1,'feeId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id,order_number) VALUES ('fooId2','this is the first procedure.',1,'feeId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id,order_number) VALUES ('fooId3','this is the first procedure.',1,'feeId',2)");
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"2\"}}")
                .when()
                .put("/boards/feeId/procedures/fooId1")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/procedures/fooId1"));
        assertEquals("2", jdbcTemplate.queryForObject("select order_number from kb_procedure where id='fooId1'", String.class));
        assertEquals("0", jdbcTemplate.queryForObject("select order_number from kb_procedure where id='fooId2'", String.class));
        assertEquals("1", jdbcTemplate.queryForObject("select order_number from kb_procedure where id='fooId3'", String.class));
    }

    @Scenario("当删除一个procedure时,如果待删除的procedure存在,则删除成功")
    @Test
    public void shouldDeleteSuccessfullyWhenTheProcedureIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
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
                .statusCode(404)
                .body("message", equalTo("procedure[fooId] is not found."));
    }

    @Scenario("通过boardId获取所有的procedure")
    @Test
    public void shouldReturnAllEntriesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id) VALUES ('fooId','this is the first procedure.',1,'feeId')");
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,reporter,board_id) VALUES ('randomId','this is the first procedure.',1,'feeId2')");
        given().header("userName", userName)
                .when()
                .get("/boards/feeId/procedures")
                .then()
                .statusCode(200)
                .body("[0].title", equalTo("this is the first procedure."))
                .body("[0].reporter", equalTo(1))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/boards/feeId/procedures/fooId"))
                .body("[0]._links.cards.href", equalTo("http://localhost:8007/procedures/fooId/cards"));
    }
}
