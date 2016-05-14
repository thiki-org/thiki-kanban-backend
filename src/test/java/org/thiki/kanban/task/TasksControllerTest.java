package org.thiki.kanban.task;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.Application;
import org.thiki.kanban.TestContextConfiguration;

import javax.sql.DataSource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;


/**
 * Created by xubt on 5/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({Application.class, TestContextConfiguration.class})
@WebIntegrationTest("server.port:8007")
public class TasksControllerTest {
    private int port = 8007;

    @Autowired
    private DataSource db;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        RestAssured.port = port;
        jdbcTemplate = new JdbcTemplate(db);
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter) VALUES (1,'this is the first entry.',1)");
    }

    @Test
    public void shouldReturn201WhenCreateTaskSuccessfully() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("select * from kb_task").size());
        given().body("{\"summary\":\"summary\",\"content\":\"foo\",\"assignee\":2,\"reporter\":2,\"entryId\":1,\"id\":1}")
                .header("userId", "11222")
                .contentType(ContentType.JSON)
                .when()
                .post("/entries/1/tasks")
                .then()
                .statusCode(201)
                .body("summary", equalTo("summary"))
                .body("content", equalTo("foo"))
                .body("assignee", equalTo(2))
                .body("reporter", equalTo(11222))
                .body("_links.self.href", equalTo("http://localhost:8007/tasks/1"))
                .body("_links.update.href", equalTo("http://localhost:8007/tasks/1"))
                .body("_links.assign.href", equalTo("http://localhost:8007/tasks/1/assignment/?assignee=2"));
        assertEquals(1, jdbcTemplate.queryForList("select * from kb_task").size());
    }

    @Test
    public void shouldReturnTasksWhenFindTasksByEntryIdSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_task (id,summary,content,assignee,reporter,entry_id) VALUES (1,'this is the task summary.','play badminton',1,1,1)");
        given().header("userId", "11222")
                .when()
                .get("/entries/1/tasks")
                .then()
                .statusCode(200)
                .body("tasks[0].summary", equalTo("this is the task summary."))
                .body("tasks[0].content", equalTo("play badminton"))
                .body("tasks[0].assignee", equalTo(1))
                .body("tasks[0].reporter", equalTo(1))
                .body("tasks[0]._links.self.href", equalTo("http://localhost:8007/tasks/1"))
                .body("tasks[0]._links.update.href", equalTo("http://localhost:8007/tasks/1"))
                .body("tasks[0]._links.assign.href", equalTo("http://localhost:8007/tasks/1/assignment/?assignee=1"));
    }

    @After
    public void resetDB() {
        jdbcTemplate.execute("TRUNCATE TABLE kb_entry");
        jdbcTemplate.execute("TRUNCATE TABLE kb_task");
    }
}
