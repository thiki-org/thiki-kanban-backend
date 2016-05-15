package org.thiki.kanban.entry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 5/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EntriesControllerTest extends TestBase {
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(db);
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter) VALUES (1,'this is the first entry.',1)");
    }

    @Test
    public void shouldReturnEntryWhenFindEntryById() {
        given().header("userId", "11222")
                .when()
                .get("/entries/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("this is the first entry."))
                .body("reporter", equalTo(1))
                .body("_links.all.href", equalTo("http://localhost:8007/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/entries/1"))
                .body("_links.del.href", equalTo("http://localhost:8007/entries/1"));
    }
}
