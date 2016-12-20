package org.thiki.kanban.board;

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
public class BoardOverallLoaderTest extends TestBase {

    @Scenario("获取指定用户所拥有的board")
    @Test
    public void should_return_all_the_board_data() {
        dbPreparation.table("kb_board")
                .names("id,name,owner,author")
                .values("boardId", "board-name", "someone", "someone").exec();

        dbPreparation.table("kb_procedure")
                .names("id,title,author,board_id")
                .values("procedure-fooId", "procedure-name", userName, "boardId").exec();

        dbPreparation.table("kb_card")
                .names("id,summary,content,author,procedure_id")
                .values("card-fooId", "card-summary.", "play badminton", userName, "procedure-fooId").exec();

        dbPreparation.table("kb_card_assignment")
                .names("id,card_id,assignee,assigner,author")
                .values("fooId", "card-fooId", "assigneeId-foo", "assignerId-foo", "authorId-foo").exec();

        given().header("userName", "someone")
                .log().all()
                .when()
                .get("/someone/boards/boardId/overall")
                .then()
                .statusCode(200)
                .body("name", equalTo("board-name"))
                .body("author", equalTo("someone"))
                .body("creationTime", notNullValue())
                .body("_links.all.href", endsWith("/someone/boards"))
                .body("_links.self.href", endsWith("/someone/boards/boardId"))
                .body("_links.procedures.href", endsWith("/boards/boardId/procedures"))
                .body("procedures", notNullValue())
                .body("procedures.procedures[0].cards", notNullValue())
                .body("procedures.procedures[0].cards.cards[0].assignments", notNullValue());
    }
}
