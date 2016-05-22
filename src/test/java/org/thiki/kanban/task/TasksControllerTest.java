package org.thiki.kanban.task;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;


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
        assertEquals(0, jdbcTemplate.queryForList("select * from kb_task").size());
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
        assertEquals(1, jdbcTemplate.queryForList("select * from kb_task").size());
    }

    @Test
    public void shouldReturnTasksWhenFindTasksByEntryIdSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,assignee,reporter,entry_id) VALUES (1,'this is the task summary.','play badminton',1,1,'fooId')");
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
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,assignee,reporter,entry_id) VALUES (1,'this is the task summary.','play badminton',1,1,1)");
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
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,assignee,reporter,entry_id) VALUES (1,'this is the task summary.','play badminton',1,1,1)");
        given().header("userId", "11222")
                .when()
                .get("/entries/2/tasks")
                .then()
                .statusCode(404)
                .body("message", equalTo("entry[2] is not found."))
                .body("code", equalTo(404));
    }

    @Test
    public void shouldReturn200WhenUpdateTaskSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,assignee,reporter,entry_id) VALUES ('fooId','this is the task summary.','play badminton',1,1,1)");
        given().body("{\"summary\":\"newSummary\"}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .put("/entries/1/tasks/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/fooId"))
                .body("_links.tasks.href", equalTo("http://localhost:8007/entries/1/tasks"));
        assertEquals("newSummary", jdbcTemplate.queryForObject("select summary from kb_task where id='fooId'", String.class));
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenTaskToUpdateIsNotExist() throws Exception {
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
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,assignee,reporter,entry_id) VALUES ('fooId','this is the task summary.','play badminton',1,1,1)");
        given().header("userId", "11222")
                .when()
                .delete("/entries/feeId/tasks/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_task WHERE  delete_status=1").size());
    }
}
