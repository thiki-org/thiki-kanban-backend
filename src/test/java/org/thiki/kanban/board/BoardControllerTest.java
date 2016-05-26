package org.thiki.kanban.board;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 5/18/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardControllerTest extends TestBase {

    @Test
    public void shouldReturn201WhenCreateBoardSuccessfully() {
        given().header("userId", "11222")
                .body("{\"name\":\"board-name\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("reporter", equalTo(11222))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/fooId"));
    }

    @Test
    public void shouldReturnBoardWhenBoardIsExists() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name',1)");
        given().header("userId", "11222")
                .when()
                .get("/boards/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("reporter", equalTo(1))
                .body("_links.all.href", equalTo("http://localhost:8007/boards"))
                .body("_links.entries.href", equalTo("http://localhost:8007/boards/fooId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/fooId"));
    }


    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name',1)");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"new-name\"}")
                .when()
                .put("/boards/fooId")
                .then()
                .statusCode(200)
                .body("name", equalTo("new-name"))
                .body("reporter", equalTo(1))
                .body("_links.all.href", equalTo("http://localhost:8007/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/fooId"));
        assertEquals("new-name", jdbcTemplate.queryForObject("select name from kb_board where id='fooId'", String.class));
    }

    @Test
    public void shouldDeleteSuccessfullyWhenTheBoardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name',1)");
        given().header("userId", "11222")
                .when()
                .delete("/boards/fooId")
                .then()
                .statusCode(200)
                .body("_links.all.href", equalTo("http://localhost:8007/boards"));
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_board WHERE  delete_status=1").size());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenEntryToDeleteIsNotExist() throws Exception {
        given().header("userId", "11222")
                .when()
                .delete("/boards/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("board[fooId] is not found."));
    }

    @Test
    public void shouldReturnAllEntriesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,reporter) VALUES ('fooId','board-name',1)");
        given().header("userId", "11222")
                .when()
                .get("/boards")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("board-name"))
                .body("[0].reporter", equalTo(1))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.all.href", equalTo("http://localhost:8007/boards"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/boards/fooId"))
                .body("[0]._links.entries.href", equalTo("http://localhost:8007/boards/fooId/entries"));
    }
}
