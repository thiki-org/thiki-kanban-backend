package org.thiki.kanban.entry;

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
 * Created by xubt on 5/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EntriesControllerTest extends TestBase {

    @Test
    public void shouldReturn201WhenCreateEntrySuccessfully() {
        given().header("userId", "11222")
                .body("{\"title\":\"this is the entry title.\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the entry title."))
                .body("reporter", equalTo(11222))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.tasks.href", equalTo("http://localhost:8007/entries/fooId/tasks"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"));
    }

    @Test
    public void shouldFailedIfEntryTitleIsEmpty() {
        given().header("userId", "11222")
                .body("{\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("列表名称不能为空"));
    }

    @Test
    public void shouldFailedIfEntryBoardIdIsEmpty() {
        given().header("userId", "11222")
                .body("{\"title\":\"this is the entry title.\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("boardId不能为空"));
    }

    @Test
    public void shouldReturnBadRequestWhenEntryTitleIsTooLong() {
        given().header("userId", "11222")
                .body("{\"boardId\":\"feeId\"}}")
                .body("{\"title\":\"\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("列表名称长度非法,有效长度为1~50个字符。"));
    }

    @Test
    public void shouldReturnEntryWhenFindEntryById() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        given().header("userId", "11222")
                .when()
                .get("/boards/feeId/entries/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("title", equalTo("this is the first entry."))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"));
    }

    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/entries/fooId")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"));
        assertEquals("newTitle", jdbcTemplate.queryForObject("select title from kb_entry where id='fooId'", String.class));
    }

    @Test
    public void shouldFailedWhenTheEntryToUpdateIsNotExists() {
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/entries/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("entry[fooId] is not found."));
    }

    @Test
    public void update_shouldResortSuccessfullyWhenCurrentSortNumberIsLessThanOriginNumber() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId1','this is the first entry.',1,'feeId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId2','this is the first entry.',1,'feeId',1)");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}}")
                .when()
                .put("/boards/feeId/entries/fooId2")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId2"));
        assertEquals("1", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId1'", String.class));
        assertEquals("0", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId2'", String.class));
    }

    @Test
    public void update_shouldResortSuccessfullyWhenCurrentSortNumberIsMoreThanOriginNumber() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId1','this is the first entry.',1,'feeId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId2','this is the first entry.',1,'feeId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId3','this is the first entry.',1,'feeId',2)");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"2\"}}")
                .when()
                .put("/boards/feeId/entries/fooId1")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId1"));
        assertEquals("2", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId1'", String.class));
        assertEquals("0", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId2'", String.class));
        assertEquals("1", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId3'", String.class));
    }

    @Test
    public void shouldDeleteSuccessfullyWhenTheEntryIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        given().header("userId", "11222")
                .when()
                .delete("/boards/feeId/entries/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_entry WHERE  delete_status=1").size());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenEntryToDeleteIsNotExist() throws Exception {
        given().header("userId", "11222")
                .when()
                .delete("/boards/feeId/entries/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("entry[fooId] is not found."));
    }

    @Test
    public void shouldReturnAllEntriesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('randomId','this is the first entry.',1,'feeId2')");
        given().header("userId", "11222")
                .when()
                .get("/boards/feeId/entries")
                .then()
                .statusCode(200)
                .body("[0].title", equalTo("this is the first entry."))
                .body("[0].reporter", equalTo(1))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"))
                .body("[0]._links.tasks.href", equalTo("http://localhost:8007/entries/fooId/tasks"));
    }
}
