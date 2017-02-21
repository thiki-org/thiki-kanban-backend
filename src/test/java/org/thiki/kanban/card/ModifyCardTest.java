package org.thiki.kanban.card;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 21/02/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ModifyCardTest extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.','someone','board-id-foo')");
    }

    @Scenario("将卡片置为子卡片")
    @Test
    public void moveCardToParentCard() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,code) VALUES ('card-parent-id','this is the card summary.','play badminton','someone','fooId','code-foo')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,code) VALUES ('fooId','this is the card summary.','play badminton','someone','fooId','code-fee')");
        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"stageId\":\"fooId\",\"parentId\":\"card-parent-id\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(200)
                .body("parentId", equalTo("card-parent-id"));
    }
}
