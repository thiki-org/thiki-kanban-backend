package org.thiki.kanban.assignment;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by xubt on 6/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AssignmentControllerTest extends TestBase {

    @Test
    public void assign_shouldReturn201WhenAssigningSuccessfully() {
        given().header("userId", "11222")
                .body("{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/entries/1/tasks/fooId/assignments")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("assignee", equalTo("assigneeId"))
                .body("assigner", equalTo("assignerId"))
                .body("reporter", equalTo("11222"))
                .body("creationTime", notNullValue())
                .body("_links.task.href", equalTo("http://localhost:8007/entries/1/tasks/fooId"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/entries/1/tasks/fooId/assignments"))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/fooId/assignments/fooId"));
    }

    @Test
    public void findById_shouldReturnAssignmentSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_task_assignment (id,task_id,assignee,assigner,reporter) VALUES ('fooId','taskId-foo','assigneeId-foo','assignerId-foo','reporterId-foo')");
        given().header("userId", "reporterId-foo")
                .body("{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}")
                .contentType(ContentType.JSON)
                .when()
                .get("/entries/1/tasks/fooId/assignments/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("assignee", equalTo("assigneeId-foo"))
                .body("assigner", equalTo("assignerId-foo"))
                .body("reporter", equalTo("reporterId-foo"))
                .body("creationTime", notNullValue())
                .body("_links.task.href", equalTo("http://localhost:8007/entries/1/tasks/fooId"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/entries/1/tasks/fooId/assignments"))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1/tasks/fooId/assignments/fooId"));
    }

    @Test
    public void findByTaskId_shouldReturnAssignmentsSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,reporter,entry_id) VALUES ('taskId-foo','this is the task summary.','play badminton',1,'fooId')");
        jdbcTemplate.execute("INSERT INTO  kb_task_assignment (id,task_id,assignee,assigner,reporter) VALUES ('fooId','taskId-foo','assigneeId-foo','assignerId-foo','reporterId-foo')");
        given().header("userId", "reporterId-foo")
                .body("{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}")
                .contentType(ContentType.JSON)
                .when()
                .get("/entries/1/tasks/taskId-foo/assignments")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo("fooId"))
                .body("[0].assignee", equalTo("assigneeId-foo"))
                .body("[0].assigner", equalTo("assignerId-foo"))
                .body("[0].reporter", equalTo("reporterId-foo"))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.task.href", equalTo("http://localhost:8007/entries/1/tasks/taskId-foo"))
                .body("[0]._links.assignments.href", equalTo("http://localhost:8007/entries/1/tasks/taskId-foo/assignments"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/entries/1/tasks/taskId-foo/assignments/fooId"));
    }
}
