package org.thiki.kanban.task;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * Created by xubt on 5/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TasksControllerTest extends TestBase {

    @Before
    public void setUp() {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter) VALUES ('fooId','this is the first entry.',1)");
    }

    @Test
    public void shouldReturn201WhenCreateTaskSuccessfully() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_task").size());
        given().body("{\"summary\":\"summary\"}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .post("/entries/fooId/tasks")
                .then()
                .statusCode(201)
                .body("summary", equalTo("summary"))
                .body("reporter", equalTo(11222))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/fooId/tasks/fooId"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_task").size());
    }

    @Test
    public void shouldReturnTasksWhenFindTasksByEntryIdSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id) VALUES (1,'this is the task summary.','play badminton',1,'fooId')");
        given().header("userId", "11222")
                .when()
                .get("/entries/fooId/tasks")
                .then()
                .statusCode(200)
                .body("tasks[0].summary", equalTo("this is the task summary."))
                .body("tasks[0].content", equalTo("play badminton"))
                .body("tasks[0].reporter", equalTo(1))
                .body("tasks[0].entryId", equalTo("fooId"))
                .body("tasks[0]._links.self.href", equalTo("http://localhost:8007/entries/fooId/tasks/1"))
                .body("tasks[0]._links.tasks.href", equalTo("http://localhost:8007/entries/fooId/tasks"));
    }

    @Test
    public void findById_shouldReturnTaskSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id) VALUES (1,'this is the task summary.','play badminton',1,1)");
        given().header("userId", "11222")
                .when()
                .get("/entries/1/tasks/1")
                .then()
                .statusCode(200)
                .body("summary", equalTo("this is the task summary."))
                .body("content", equalTo("play badminton"))
                .body("reporter", equalTo(1))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/1"))
                .body("_links.tasks.href", equalTo("http://localhost:8007/entries/1/tasks"));
    }

    @Test
    public void findTasksByEntryId_shouldReturn404WhenEntryIsNotFound() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id) VALUES (1,'this is the task summary.','play badminton',1,1)");
        given().header("userId", "11222")
                .when()
                .get("/entries/2/tasks")
                .then()
                .statusCode(404)
                .body("message", equalTo("entry[2] is not found."))
                .body("code", equalTo(404));
    }

    @Test
    public void update_shouldReturn200WhenUpdateTaskSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id) VALUES ('fooId','this is the task summary.','play badminton',1,1)");
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":3,\"entryId\":1}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .put("/entries/1/tasks/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(3))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/fooId"))
                .body("_links.tasks.href", equalTo("http://localhost:8007/entries/1/tasks"));
        assertEquals("newSummary", jdbcTemplate.queryForObject("SELECT summary FROM kb_task WHERE id='fooId'", String.class));
    }

    @Test
    public void update_shouldResortSuccessfullyWhenCurrentOrderNumberLessThanOriginNumber() throws Exception {
        prepareDataForResort();
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":1,\"entryId\":1}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .put("/entries/1/tasks/fooId4")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(1))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/fooId4"))
                .body("_links.tasks.href", equalTo("http://localhost:8007/entries/1/tasks"));

        assertEquals(0, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId1'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId2'"));
        assertEquals(3, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId3'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId4'"));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId5'"));
    }

    private void prepareDataForResort() {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id,order_number) VALUES ('fooId1','this is the task summary.','play badminton',1,1,0)");
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id,order_number) VALUES ('fooId2','this is the task summary.','play badminton',1,1,1)");
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id,order_number) VALUES ('fooId3','this is the task summary.','play badminton',1,1,2)");
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id,order_number) VALUES ('fooId4','this is the task summary.','play badminton',1,1,3)");
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id,order_number) VALUES ('fooId5','this is the task summary.','play badminton',1,1,4)");
    }

    @Test
    public void update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumber() throws Exception {
        prepareDataForResort();
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":4,\"entryId\":1}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .put("/entries/1/tasks/fooId4")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(4))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/fooId4"))
                .body("_links.tasks.href", equalTo("http://localhost:8007/entries/1/tasks"));

        assertEquals(0, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId1'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId2'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId3'"));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId4'"));
        assertEquals(3, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId5'"));
    }


    @Test
    public void update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumberButNotTheBiggest() throws Exception {
        prepareDataForResort();
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":3,\"entryId\":1}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .put("/entries/1/tasks/fooId1")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(3))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/fooId1"))
                .body("_links.tasks.href", equalTo("http://localhost:8007/entries/1/tasks"));

        assertEquals(3, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId1'"));
        assertEquals(0, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId2'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId3'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId4'"));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT order_number FROM kb_task WHERE id='fooId5'"));
    }

    @Test
    public void update_shouldThrowResourceNotFoundExceptionWhenTaskToUpdateIsNotExist() throws Exception {
        given().body("{\"summary\":\"newSummary\"}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .put("/entries/1/tasks/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("entry[fooId] is not found, task update failed."));
    }

    @Test
    public void shouldDeleteSuccessfullyWhenTheEntryIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id) VALUES ('fooId','this is the task summary.','play badminton',1,1)");
        given().header("userId", "11222")
                .when()
                .delete("/entries/feeId/tasks/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_task WHERE  delete_status=1").size());
    }
}
