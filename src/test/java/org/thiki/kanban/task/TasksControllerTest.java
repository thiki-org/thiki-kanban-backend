package org.thiki.kanban.task;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
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
    }

    @Test
    public void shouldReturn201WhenCreateTaskSuccessfully() throws Exception {
        given().body("{\"summary\":\"summary\",\"content\":\"323111\",\"assignee\":2,\"entryId\":2,\"reporter\":2,\"id\":1}")
                .contentType(ContentType.JSON).
                when().
                post("/1/entry/2/tasks").
                then().statusCode(201);

        assertEquals(1, jdbcTemplate.queryForList("select * from kb_task").size());
    }
}
