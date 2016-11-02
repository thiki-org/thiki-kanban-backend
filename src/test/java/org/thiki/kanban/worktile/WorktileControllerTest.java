package org.thiki.kanban.worktile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import java.io.File;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 11/1/16.
 */
@Domain(order = DomainOrder.WORKTILE, name = "Worktile")
@RunWith(SpringJUnit4ClassRunner.class)
public class WorktileControllerTest extends TestBase {

    @Scenario("数据导入>数据导入时,新建worktile board")
    @Test
    public void createNewBoard() {
        File worktileTasks = new File("src/test/resources/worktile_tasks.json");

        given().header("userName", "someone")
                .multiPart("worktileTasks", worktileTasks)
                .when()
                .post("/someone/worktileTasks")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("worktile"))
                .body("author", equalTo("someone"))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"));

        assertEquals("worktile", jdbcTemplate.queryForObject("select name from kb_board where author='someone'", String.class));
    }

    @Scenario("数据导入>新建board后,导入entry")
    @Test
    public void importEntry() {
        File worktileTasks = new File("src/test/resources/worktile_tasks.json");

        given().header("userName", "someone")
                .multiPart("worktileTasks", worktileTasks)
                .when()
                .post("/someone/worktileTasks")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("worktile"))
                .body("author", equalTo("someone"))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"));

        assertEquals("Product Backlog", jdbcTemplate.queryForObject("select title from kb_procedure where board_id='fooId'", String.class));
    }

    @Scenario("数据导入>新建board后,导入tasks")
    @Test
    public void importCards() {
        File worktileTasks = new File("src/test/resources/worktile_tasks.json");

        given().header("userName", "someone")
                .multiPart("worktileTasks", worktileTasks)
                .when()
                .post("/someone/worktileTasks")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("worktile"))
                .body("author", equalTo("someone"))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/someone/boards"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/boards/fooId"));

        assertEquals("任务名称", jdbcTemplate.queryForObject("select summary from kb_card where author='someone'", String.class));
    }
}
