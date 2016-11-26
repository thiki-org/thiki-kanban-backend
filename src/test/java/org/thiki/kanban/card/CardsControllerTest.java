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

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * Created by xubt on 5/11/16.
 */
@Domain(order = DomainOrder.CARD, name = "卡片")
@RunWith(SpringJUnit4ClassRunner.class)
public class CardsControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author,board_id) VALUES ('fooId','this is the first procedure.','someone','board-id-foo')");
    }

    @Scenario("创建一个新的卡片")
    @Test
    public void create_shouldReturn201WhenCreateCardSuccessfully() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        given().body("{\"summary\":\"summary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/fooId/cards")
                .then()
                .statusCode(201)
                .body("summary", equalTo("summary"))
                .body("author", equalTo(userName))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/fooId"))
                .body("_links.cards.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards"))
                .body("_links.acceptanceCriterias.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/fooId/acceptanceCriterias"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/fooId/assignments"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }

    @Scenario("当创建一个卡片时,如果卡片概述为空,则创建失败")
    @Test
    public void create_shouldFailedIfSummaryIsNull() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        given().body("{\"summary\":\"\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/fooId/cards")
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
                .post("/boards/boardId-foo/procedures/fooId/cards")
                .then()
                .statusCode(400)
                .body("message", equalTo(CardsCodes.summaryIsInvalid));

        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }

    @Scenario("当创建一个卡片时,如果卡片所属的procedure并不存在,则创建失败")
    @Test
    public void create_shouldCreateFailedWhenProcedureIsNotFound() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        given().body("{\"summary\":\"summary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/non-exists-procedureId/cards")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("procedure[non-exists-procedureId] is not found."));
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }

    @Scenario("当根据procedureId查找其下属的卡片时,可以返回其所有卡片")
    @Test
    public void shouldReturnCardsWhenFindCardsByProcedureIdSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES ('card-fooId','this is the card summary.','play badminton','someone','fooId')");
        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/procedures/fooId/cards")
                .then()
                .statusCode(200)
                .body("cards[0].summary", equalTo("this is the card summary."))
                .body("cards[0].content", equalTo("play badminton"))
                .body("cards[0].author", equalTo(userName))
                .body("cards[0].procedureId", equalTo("fooId"))
                .body("cards[0]._links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/card-fooId"))
                .body("cards[0]._links.cards.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards"))
                .body("cards[0]._links.acceptanceCriterias.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/card-fooId/acceptanceCriterias"))
                .body("cards[0]._links.comments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/card-fooId/comments"))
                .body("cards[0]._links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/card-fooId/assignments"))
                .body("cards[0]._links.cardTags.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/card-fooId/tags"))
                .body("cards[0]._links.tags.href", equalTo("http://localhost:8007/boards/boardId-foo/tags"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards"))
                .body("_links.sortNumbers.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/fooId/cards/sortNumbers"));
    }

    @Scenario("根据ID查找一个卡片时,如果卡片存在,则返回该卡片")
    @Test
    public void findById_shouldReturnCardSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES (1,'this is the card summary.','play badminton','someone',1)");
        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/procedures/1/cards/1")
                .then()
                .statusCode(200)
                .body("summary", equalTo("this is the card summary."))
                .body("content", equalTo("play badminton"))
                .body("author", equalTo(userName))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/1"))
                .body("_links.cards.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/1/assignments"));
    }

    @Scenario("根据ID查找一个卡片时,如果卡片不存在,则抛出400错误")
    @Test
    public void update_shouldFailedWhenCardIsNotExist() throws Exception {
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .get("/boards/boardId-foo/procedures/fooId/cards/feeId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.message()));
    }

    @Scenario("当根据procedureID查找卡片时,如果procedure不存在,则抛出404异常")
    @Test
    public void findCardsByProcedureId_shouldReturn404WhenProcedureIsNotFound() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES (1,'this is the card summary.','play badminton',1,1)");
        given().header("userName", userName)
                .when()
                .get("/boards/boardId-foo/procedures/2/cards")
                .then()
                .statusCode(404)
                .body("message", equalTo("procedure[2] is not found."))
                .body("code", equalTo(404));
    }

    @Scenario("更新卡片成功")
    @Test
    public void update_shouldReturn200WhenUpdateCardSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES ('fooId','this is the card summary.','play badminton',1,1)");
        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"procedureId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/procedures/1/cards/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("sortNumber", equalTo(3))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId"))
                .body("_links.cards.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId/assignments"));
        assertEquals("newSummary", jdbcTemplate.queryForObject("SELECT summary FROM kb_card WHERE id='fooId'", String.class));
    }

    @Scenario("当一个卡片从某个procedure移动到另一个procedure时,不仅需要重新排序目标procedure,也要对原始procedure排序")
    @Test
    public void update_shouldResortSuccessfullyWhenCardIsFromAntherProcedure() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,sort_number) VALUES ('fooId1','summary1','play badminton',1,'procedure-fooId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,sort_number) VALUES ('fooId2','summary2','play badminton',1,'procedure-fooId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,sort_number) VALUES ('fooId6','this is the card summary.','play badminton',1,2,3)");

        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"procedureId\":1,\"code\":\"code-foo\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/procedures/1/cards/fooId6")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("sortNumber", equalTo(3))
                .body("code", equalTo("code-foo"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId6"))
                .body("_links.cards.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId6/assignments"));
    }

    @Scenario("当更新一个卡片时,如果待更新的卡片不存在,则抛出资源不存在的错误")
    @Test
    public void update_shouldThrowResourceNotFoundExceptionWhenCardToUpdateIsNotExist() throws Exception {
        given().body("{\"summary\":\"newSummary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/procedures/1/cards/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.message()));
    }

    @Scenario("编号检查>当更新一个卡片时,如果当前看版中已经存在相同编号,则不予许更新")
    @Test
    public void notAllowedIfCodeIsAlreadyExist() throws Exception {

        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,sort_number,code) VALUES ('fooId1','summary1','play badminton',1,'procedure-fooId',0,'code1')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,sort_number,code) VALUES ('fooId2','summary2','play badminton',1,'procedure-fooId',1,'code2')");

        given().body("{\"summary\":\"newSummary\",\"sortNumber\":3,\"procedureId\":\"procedure-fooId\",\"code\":\"code2\"}")

                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/boardId-foo/procedures/1/cards/fooId1")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CODE_IS_ALREADY_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CODE_IS_ALREADY_EXISTS.message()));
    }

    @Scenario("当删除一个卡片时,如果卡片存在,则删除成功")
    @Test
    public void delete_shouldDeleteSuccessfullyWhenTheCardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES ('fooId','this is the card summary.','play badminton',1,1)");
        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/procedures/feeId/cards/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_card WHERE  delete_status=1").size());
    }

    @Scenario("当删除一个卡片时,如果待删除的卡片不存在,400")
    @Test
    public void delete_shouldDeleteFailedWhenTheCardIsNotExist() {
        given().header("userName", userName)
                .when()
                .delete("/boards/boardId-foo/procedures/feeId/cards/non-exists-cardId")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.message()));
        ;
    }
}
