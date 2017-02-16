package org.thiki.kanban.card;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.foundation.common.date.DateService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;


/**
 * Created by xubt on 5/11/16.
 */
@Domain(order = DomainOrder.CARD, name = "卡片")
@RunWith(SpringJUnit4ClassRunner.class)
public class CardsControllerTest extends TestBase {

    @Resource
    private DateService dateService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id) VALUES ('fooId','this is the first stage.','someone','board-id-foo')");
    }

    @Scenario("创建一个新的卡片")
    @Test
    public void create_shouldReturn201WhenCreateCardSuccessfully() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        String expectedCode = generateExpectedCode();
        int expireDays = 5;
        String currentTime = DateService.instance().getNow_EN();
        String deadline = DateService.instance().addDay(currentTime, expireDays);
        Map newCard = new HashMap();
        newCard.put("summary", "summary");
        newCard.put("deadline", deadline);
        newCard.put("size", 5);
        given().body(newCard)
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/fooId/cards")
                .then()
                .statusCode(201)
                .body("summary", equalTo("summary"))
                .body("author", equalTo(userName))
                .body("code", equalTo(expectedCode))
                .body("code", equalTo(expectedCode))
                .body("size", equalTo(5))
                .body("sizeName", equalTo(CardsCodes.sizeName(5)))
                .body("restDays", equalTo(expireDays))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/fooId/cards/fooId"))
                .body("_links.cards.href", endsWith("/boards/boardId-foo/stages/fooId/cards"))
                .body("_links.acceptanceCriterias.href", endsWith("/boards/boardId-foo/stages/fooId/cards/fooId/acceptanceCriterias"))
                .body("_links.assignments.href", endsWith("/boards/boardId-foo/stages/fooId/cards/fooId/assignments"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_activity where card_id='fooId'").size());
    }

    private String generateExpectedCode() {
        String codePrefix = "H";
        dbPreparation.table("kb_board").names("id,name,author,code_prefix").values("boardId-foo", "board-name", "someone", codePrefix).exec();
        return codePrefix + dateService.simpleDate() + "01";
    }

    @Scenario("当创建一个卡片时,如果卡片概述为空,则创建失败")
    @Test
    public void create_shouldFailedIfSummaryIsNull() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        given().body("{\"summary\":\"\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/fooId/cards")
                .then()
                .statusCode(400)
                .body("message", equalTo(CardsCodes.summaryIsRequired));
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }

    @Scenario("当创建一个卡片时,如果卡片概述长度超过50,则创建失败")
    @Test
    public void create_shouldFailedIfSummaryIsTooLong() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());

        given().body("{\"summary\":\"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/fooId/cards")
                .then()
                .statusCode(400)
                .body("message", equalTo(CardsCodes.summaryIsInvalid));

        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }

    @Scenario("当根据stageId查找其下属的卡片时,可以返回其所有卡片")
    @Test
    public void shouldReturnCardsWhenFindCardsByStageIdSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('card-fooId','this is the card summary.','play badminton','someone','fooId')");
        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/stages/fooId/cards")
                .then()
                .statusCode(200)
                .body("cards[0].summary", equalTo("this is the card summary."))
                .body("cards[0].content", equalTo("play badminton"))
                .body("cards[0].author", equalTo(userName))
                .body("cards[0].stageId", equalTo("fooId"))
                .body("cards[0]._links.self.href", endsWith("/boards/boardId-foo/stages/fooId/cards/card-fooId"))
                .body("cards[0]._links.cards.href", endsWith("/boards/boardId-foo/stages/fooId/cards"))
                .body("cards[0]._links.acceptanceCriterias.href", endsWith("/boards/boardId-foo/stages/fooId/cards/card-fooId/acceptanceCriterias"))
                .body("cards[0]._links.comments.href", endsWith("/boards/boardId-foo/stages/fooId/cards/card-fooId/comments"))
                .body("cards[0]._links.assignments.href", endsWith("/boards/boardId-foo/stages/fooId/cards/card-fooId/assignments"))
                .body("cards[0]._links.cardTags.href", endsWith("/boards/boardId-foo/stages/fooId/cards/card-fooId/tags"))
                .body("cards[0]._links.tags.href", endsWith("/boards/boardId-foo/tags"))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/fooId/cards"))
                .body("_links.sortNumbers.href", endsWith("/boards/boardId-foo/stages/fooId/cards/sortNumbers"));
    }

    @Scenario("根据ID查找一个卡片时,如果卡片存在,则返回该卡片")
    @Test
    public void findById_shouldReturnCardSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES (1,'this is the card summary.','play badminton','someone',1)");
        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/stages/1/cards/1")
                .then()
                .statusCode(200)
                .body("summary", equalTo("this is the card summary."))
                .body("content", equalTo("play badminton"))
                .body("author", equalTo(userName))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/1/cards/1"))
                .body("_links.cards.href", endsWith("/boards/boardId-foo/stages/1/cards"))
                .body("_links.assignments.href", endsWith("/boards/boardId-foo/stages/1/cards/1/assignments"));
    }

    @Scenario("根据ID查找一个卡片时,如果卡片不存在,则抛出400错误")
    @Test
    public void update_shouldFailedWhenCardIsNotExist() throws Exception {
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .get("/boards/boardId-foo/stages/fooId/cards/feeId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.message()));
    }

    @Scenario("更新卡片成功")
    @Test
    public void update_shouldReturn200WhenUpdateCardSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,deadline,author,stage_id) VALUES ('fooId','this is the card summary.','play badminton','2017-02-13',1,1)");
        dbPreparation.table("kb_board").names("id,name,author,code_prefix").values("boardId-foo", "board-name", "someone", "H").exec();
        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"stageId\":1,\"deadline\":\"2017-02-15\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("sortNumber", equalTo(3))
                .body("deadline", equalTo("2017-02-15"))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/1/cards/fooId"))
                .body("_links.cards.href", endsWith("/boards/boardId-foo/stages/1/cards"))
                .body("_links.assignments.href", endsWith("/boards/boardId-foo/stages/1/cards/fooId/assignments"));
        assertEquals("newSummary", jdbcTemplate.queryForObject("SELECT summary FROM kb_card WHERE id='fooId'", String.class));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_activity where card_id='fooId'").size());
    }

    @Scenario("更新卡片时，如果卡片的编号为空，则自动生成编号")
    @Test
    public void shouldAutoGenerateCodeIfCodeWasNullWhenUpdateCard() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('fooId','this is the card summary.','play badminton',1,1)");
        String expectedCode = generateExpectedCode();
        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"stageId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("code", equalTo(expectedCode))
                .body("sortNumber", equalTo(3))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/1/cards/fooId"))
                .body("_links.cards.href", endsWith("/boards/boardId-foo/stages/1/cards"))
                .body("_links.assignments.href", endsWith("/boards/boardId-foo/stages/1/cards/fooId/assignments"));
        assertEquals("newSummary", jdbcTemplate.queryForObject("SELECT summary FROM kb_card WHERE id='fooId'", String.class));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_activity where card_id='fooId'").size());
    }


    @Scenario("更新卡片生成编号时，编号总数总本月开始算起")
    @Test
    public void shouldOnlyCountCurrentMonthWhenUpdateCard() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,code) VALUES ('other-fooId','this is the card summary.','play badminton',1,1,'H170101')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('fooId','this is the card summary.','play badminton',1,1)");
        String expectedCode = generateExpectedCode();
        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"stageId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("code", equalTo(expectedCode))
                .body("sortNumber", equalTo(3))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/1/cards/fooId"))
                .body("_links.cards.href", endsWith("/boards/boardId-foo/stages/1/cards"))
                .body("_links.assignments.href", endsWith("/boards/boardId-foo/stages/1/cards/fooId/assignments"));
        assertEquals("newSummary", jdbcTemplate.queryForObject("SELECT summary FROM kb_card WHERE id='fooId'", String.class));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_activity where card_id='fooId'").size());
    }

    @Scenario("当一个卡片从某个stage移动到另一个stage时,不仅需要重新排序目标stage,也要对原始stage排序")
    @Test
    public void update_shouldResortSuccessfullyWhenCardIsFromAntherStage() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,sort_number) VALUES ('fooId1','summary1','play badminton',1,'stage-fooId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,sort_number) VALUES ('fooId2','summary2','play badminton',1,'stage-fooId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id,sort_number) VALUES ('fooId6','this is the card summary.','play badminton',1,2,3)");
        String expectedCode = generateExpectedCode();

        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"stageId\":\"fooId\",\"code\":\"code-foo\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/fooId/cards/fooId6")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("sortNumber", equalTo(3))
                .body("code", equalTo(expectedCode))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/fooId/cards/fooId6"))
                .body("_links.cards.href", endsWith("/boards/boardId-foo/stages/fooId/cards"))
                .body("_links.assignments.href", endsWith("/boards/boardId-foo/stages/fooId/cards/fooId6/assignments"));
    }

    @Scenario("当更新一个卡片时,如果待更新的卡片不存在,则抛出资源不存在的错误")
    @Test
    public void update_shouldThrowResourceNotFoundExceptionWhenCardToUpdateIsNotExist() throws Exception {
        given().body("{\"summary\":\"newSummary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.message()));
    }

    @Scenario("挪动卡片到某列时,如果该列已经超额，则不允许挪动")
    @Test
    public void movingWasNotAllowedIfStageExceededTheWipLimit() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,wip_limit) VALUES ('stage-fooId','this is the first stage.','someone','board-id-foo',1)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('fooId','this is the card summary.','play badminton',1,'stage-fooId-other')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('fooId-other','this is the card summary.','play badminton',1,'stage-fooId')");
        given().body("{\"summary\":\"newSummary\",\"stageId\":\"stage-fooId\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/stages/1/cards/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.STAGE_WIP_REACHED_LIMIT.code()))
                .body("message", equalTo(CardsCodes.STAGE_WIP_REACHED_LIMIT.message()));
    }

    @Scenario("当删除一个卡片时,如果卡片存在,则删除成功")
    @Test
    public void delete_shouldDeleteSuccessfullyWhenTheCardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('fooId','this is the card summary.','play badminton',1,1)");
        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/stages/feeId/cards/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_card WHERE  delete_status=1").size());
    }

    @Scenario("当删除一个卡片时,如果待删除的卡片不存在,400")
    @Test
    public void delete_shouldDeleteFailedWhenTheCardIsNotExist() {
        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/stages/feeId/cards/non-exists-cardId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.message()));
    }
}
