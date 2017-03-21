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

    @Scenario("将卡片置为子卡片时，如果父卡片不存在，则不予许设置")
    @Test
    public void should_not_allowed_if_parent_card_not_exist() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,code) VALUES ('fooId','this is the card summary.','play badminton','someone','fooId','code-fee')");
        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"stageId\":\"fooId\",\"parentId\":\"card-parent-id\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.PARENT_CARD_IS_NOT_FOUND.code()))
                .body("message", equalTo(CardsCodes.PARENT_CARD_IS_NOT_FOUND.message()));
    }

    @Scenario("将卡片置为子卡片时，如果卡片存在子卡片，则不予许设置")
    @Test
    public void should_not_allowed_if_card_has_child_cards() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,code) VALUES ('card-parent-id','this is the card summary.','play badminton','someone','stage-fooId','code-foo')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,parent_id,code) VALUES ('card-child-id','this is the card summary.','play badminton','someone','stage-fooId','fooId','code-foo-fee')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,code) VALUES ('fooId','this is the card summary.','play badminton','someone','fooId','code-fee')");
        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"stageId\":\"fooId\",\"parentId\":\"card-parent-id\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.HAS_CHILD_CARD.code()))
                .body("message", equalTo(CardsCodes.HAS_CHILD_CARD.message()));
    }

    @Scenario("挪动卡片到完成列时，如果当前卡片的任务尚未完成，则不允许挪动")
    @Test
    public void should_not_allow_moving_to_done_stage_if_acceptance_criterias_is_not_completed() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('stage-fooId','this is the first stage.','someone','board-id-foo',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('card-fooId','this is the card summary.','play badminton',1,'stage-fooId-other')");
        jdbcTemplate.execute("INSERT INTO  kb_acceptance_criterias (id,summary,card_id,author) VALUES ('acceptance_criterias-fooId','summary.','card-fooId','someone')");

        given().body("[{\"id\":\"card-fooId\",\"summary\":\"newSummary\",\"stageId\":\"stage-fooId\"}]")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/movement")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.ACCEPTANCE_CRITERIAS_IS_NOT_COMPLETED.code()))
                .body("message", equalTo(CardsCodes.ACCEPTANCE_CRITERIAS_IS_NOT_COMPLETED.message()));
    }

    @Test
    public void should_not_allow_moving_to_done_stage_if_acceptance_criterias_is_not_set() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('stage-fooId','this is the first stage.','someone','board-id-foo',1,9)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('card-fooId','this is the card summary.','play badminton',1,'stage-fooId-other')");

        given().body("[{\"id\":\"card-fooId\",\"summary\":\"newSummary\",\"stageId\":\"stage-fooId\"}]")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/movement")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.ACCEPTANCE_CRITERIAS_IS_NOT_SET.code()))
                .body("message", equalTo(CardsCodes.ACCEPTANCE_CRITERIAS_IS_NOT_SET.message()));
    }
}
