package org.thiki.kanban.board;

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
 * Created by xubt on 5/18/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardControllerTest extends TestBase {

    @Scenario("当创建一个board时,如果参数合法,则创建成功并返回创建后的board")
    @Test
    public void shouldReturn201WhenCreateBoardSuccessfully() {
        given().header("userName", "someone")
                .body("{\"name\":\"board-name\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/boards")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("reporter", equalTo("someone"))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"));
    }

    @Scenario("用户根据ID获取board时,如果该board存在,则返回其信息")
    @Test
    public void shouldReturnBoardWhenBoardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name','someone')");
        given().header("userName", "someone")
                .when()
                .get("/someone/boards/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("reporter", equalTo("someone"))
                .body("_links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("_links.procedures.href", equalTo("http://localhost:8007/boards/fooId/procedures"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"));
    }

    @Scenario("成功更新一个board信息")
    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name','someone')");
        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"new-name\"}")
                .when()
                .put("/someone/boards/fooId")
                .then()
                .statusCode(200)
                .body("name", equalTo("new-name"))
                .body("reporter", equalTo("someone"))
                .body("_links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"));
        assertEquals("new-name", jdbcTemplate.queryForObject("select name from kb_board where id='fooId'", String.class));
    }

    @Scenario("当用户删除一个指定的board时,如果该board存在,则删除成功")
    @Test
    public void shouldDeleteSuccessfullyWhenTheBoardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name','someone')");
        given().header("userName", "someone")
                .when()
                .delete("/someone/boards/fooId")
                .then()
                .statusCode(200)
                .body("_links.all.href", equalTo("http://localhost:8007/someone/boards"));
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_board WHERE  delete_status=1").size());
    }

    @Scenario("当用户删除一个指定的board时,如果该board不存在,则返回客户端404错误")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenProcedureToDeleteIsNotExist() throws Exception {
        given().header("userName", "someone")
                .when()
                .delete("/someone/boards/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("board[fooId] is not found."));
    }

    @Scenario("获取所有的boards(这个接口考虑废弃)")
    @Test
    public void loadAll_shouldReturnAllBoardsSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name','someone')");
        given().header("userName", "someone")
                .when()
                .get("/someone/boards")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("board-name"))
                .body("[0].reporter", equalTo("someone"))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"))
                .body("[0]._links.procedures.href", equalTo("http://localhost:8007/boards/fooId/procedures"));
    }

    @Scenario("获取指定用户所拥有的boards")
    @Test
    public void findByuserName_shouldReturnAllBoardsSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name','someone')");
        given().header("userName", "someone")
                .when()
                .get("/someone/boards")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("board-name"))
                .body("[0].reporter", equalTo("someone"))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"))
                .body("[0]._links.procedures.href", equalTo("http://localhost:8007/boards/fooId/procedures"));
    }
}
