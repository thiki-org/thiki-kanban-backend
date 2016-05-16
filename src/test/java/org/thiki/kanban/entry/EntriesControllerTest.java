package org.thiki.kanban.entry;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
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
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(db);
    }

    @Test
    public void shouldReturn201WhenCreateEntrySuccessfully() {
        given().header("userId", "11222")
                .body("{\"title\":\"this is the entry title.\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/entries")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the entry title."))
                .body("reporter", equalTo(11222))
                .body("_links.all.href", equalTo("http://localhost:8007/entries"))
                .body("_links.tasks.href", notNullValue())
                .body("_links.self.href", notNullValue())
                .body("_links.del.href", notNullValue());
    }

    @Test
    public void shouldReturnEntryWhenFindEntryById() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter) VALUES ('fee','this is the first entry.',1)");
        given().header("userId", "11222")
                .when()
                .get("/entries/fee")
                .then()
                .statusCode(200)
                .body("title", equalTo("this is the first entry."))
                .body("reporter", equalTo(1))
                .body("_links.all.href", equalTo("http://localhost:8007/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/fee"))
                .body("_links.del.href", equalTo("http://localhost:8007/entries/fee"));
    }

    @Test
    public void shouldDeleteSuccessfullyWhenTheEntryIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter) VALUES ('fooId','this is the first entry.',1)");
        given().header("userId", "11222")
                .when()
                .delete("/entries/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_entry WHERE  delete_status=1").size());
    }
}
