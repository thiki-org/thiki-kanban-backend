package org.thiki.kanban.board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by xubt on 5/18/16.
 */
@Domain(order = DomainOrder.BOARD, name = "看板")
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardSnapShotLoaderTest extends TestBase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        dbPreparation.table("kb_board")
                .names("id,name,owner,author")
                .values("boardId", "board-name", "someone", "someone").exec();

        dbPreparation.table("kb_stage")
                .names("id,title,author,board_id,type")
                .values("stage-fooId", "stage-name", userName, "boardId", 1).exec();

        dbPreparation.table("kb_card")
                .names("id,summary,content,author,stage_id")
                .values("card-fooId", "card-summary.", "play badminton", userName, "stage-fooId").exec();

        dbPreparation.table("kb_card_assignment")
                .names("id,card_id,assignee,assigner,author")
                .values("fooId", "card-fooId", "assigneeId-foo", "assignerId-foo", "authorId-foo").exec();
    }

    @Scenario("获取指定用户所拥有的board")
    @Test
    public void should_return_all_the_board_data() {
        given().header("userName", "someone")
                .log().all()
                .when()
                .get("/someone/boards/boardId/snapshot")
                .then()
                .statusCode(200)
                .body("name", equalTo("board-name"))
                .body("author", equalTo("someone"))
                .body("creationTime", notNullValue())
                .body("_links.all.href", endsWith("/someone/boards"))
                .body("_links.self.href", endsWith("/someone/boards/boardId"))
                .body("_links.stages.href", endsWith("/boards/boardId/stages"))
                .body("stages", notNullValue())
                .body("stages.stages[0].cardsNode", notNullValue())
                .body("stages.stages[0].cardsNode.cards[0].assignmentsNode", notNullValue());
    }

    @Scenario("当卡片具有子卡片时，将子卡片的数据包含在当前卡片下")
    @Test
    public void should_return_child_cards_in_the_data_structure() {
        dbPreparation.table("kb_card")
                .names("id,summary,content,author,stage_id,parent_id")
                .values("card-child-fooId", "card-summary.", "play badminton", userName, "stage-fooId", "card-fooId").exec();

        given().header("userName", "someone")
                .log().all()
                .when()
                .get("/someone/boards/boardId/snapshot")
                .then()
                .statusCode(200)
                .body("stages.stages[0].cardsNode.cards[1].child.cards[0]", notNullValue());
    }
}
