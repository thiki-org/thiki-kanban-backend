package org.thiki.kanban.card;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.annotations.Theme;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * Created by xubt on 5/11/16.
 */
@Theme("卡片")
@RunWith(SpringJUnit4ClassRunner.class)
public class CardsControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_procedure (id,title,author) VALUES ('fooId','this is the first procedure.',1)");
    }

    @Scenario("创建一个新的卡片")
    @Test
    public void create_shouldReturn201WhenCreateCardSuccessfully() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        given().body("{\"summary\":\"summary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/procedures/fooId/cards")
                .then()
                .statusCode(201)
                .body("summary", equalTo("summary"))
                .body("author", equalTo(userName))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/fooId/cards/fooId"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/fooId/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/procedures/fooId/cards/fooId/assignments"));
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
                .post("/procedures/fooId/cards")
                .then()
                .statusCode(400)
                .body("message", equalTo(CardsCodes.summaryIsRequired));
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }

    @Scenario("当创建一个卡片时,如果卡片概述长度超过50,则创建失败")
    @Test
    public void create_shouldFailedIfSummaryIsTooLong() throws Exception {
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
        given().body("{\"summary\":\"长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限长度超限\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .post("/procedures/fooId/cards")
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
                .post("/procedures/non-exists-procedureId/cards")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("procedure[non-exists-procedureId] is not found."));
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_card").size());
    }

    @Scenario("当根据procedureId查找其下属的卡片时,可以返回其所有卡片")
    @Test
    public void shouldReturnCardsWhenFindCardsByProcedureIdSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES (1,'this is the card summary.','play badminton','someone','fooId')");
        given().header("userName", userName)
                .when()
                .get("/procedures/fooId/cards")
                .then()
                .statusCode(200)
                .body("[0].summary", equalTo("this is the card summary."))
                .body("[0].content", equalTo("play badminton"))
                .body("[0].author", equalTo(userName))
                .body("[0].procedureId", equalTo("fooId"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/procedures/fooId/cards/1"))
                .body("[0]._links.cards.href", equalTo("http://localhost:8007/procedures/fooId/cards"))
                .body("[0]._links.assignments.href", equalTo("http://localhost:8007/procedures/fooId/cards/1/assignments"));
    }

    @Scenario("根据ID查找一个卡片时,如果卡片存在,则返回该卡片")
    @Test
    public void findById_shouldReturnCardSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES (1,'this is the card summary.','play badminton','someone',1)");
        given().header("userName", userName)
                .when()
                .get("/procedures/1/cards/1")
                .then()
                .statusCode(200)
                .body("summary", equalTo("this is the card summary."))
                .body("content", equalTo("play badminton"))
                .body("author", equalTo(userName))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/1/cards/1"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/procedures/1/cards/1/assignments"));
    }

    @Scenario("根据ID查找一个卡片时,如果卡片不存在,则抛出404的错误")
    @Test
    public void update_shouldFailedWhenCardIsNotExist() throws Exception {
        given().header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .get("/procedures/fooId/cards/feeId")
                .then()
                .statusCode(404)
                .body("message", equalTo("card[feeId] is not found."))
                .body("code", equalTo(404));
    }

    @Scenario("当根据procedureID查找卡片时,如果procedure不存在,则抛出404异常")
    @Test
    public void findCardsByProcedureId_shouldReturn404WhenProcedureIsNotFound() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES (1,'this is the card summary.','play badminton',1,1)");
        given().header("userName", userName)
                .when()
                .get("/procedures/2/cards")
                .then()
                .statusCode(404)
                .body("message", equalTo("procedure[2] is not found."))
                .body("code", equalTo(404));
    }

    @Scenario("更新卡片成功")
    @Test
    public void update_shouldReturn200WhenUpdateCardSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES ('fooId','this is the card summary.','play badminton',1,1)");
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":3,\"procedureId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/procedures/1/cards/fooId")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(3))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/1/cards/fooId"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/procedures/1/cards/fooId/assignments"));
        assertEquals("newSummary", jdbcTemplate.queryForObject("SELECT summary FROM kb_card WHERE id='fooId'", String.class));
    }

    @Scenario("当移动一个卡片时,移动后的顺序小于其前置顺序")
    @Test
    public void update_shouldResortSuccessfullyWhenCurrentOrderNumberLessThanOriginNumber() throws Exception {
        prepareDataForResort();
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":1,\"procedureId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/procedures/1/cards/fooId4")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(1))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/1/cards/fooId4"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/1/cards"));

        assertEquals(0, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId1'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId2'"));
        assertEquals(3, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId3'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId4'"));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId5'"));
    }

    private void prepareDataForResort() {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,order_number) VALUES ('fooId1','this is the card summary.','play badminton',1,1,0)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,order_number) VALUES ('fooId2','this is the card summary.','play badminton',1,1,1)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,order_number) VALUES ('fooId3','this is the card summary.','play badminton',1,1,2)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,order_number) VALUES ('fooId4','this is the card summary.','play badminton',1,1,3)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,order_number) VALUES ('fooId5','this is the card summary.','play badminton',1,1,4)");
    }

    @Scenario("当移动一个卡片时,移动后的顺序大于初始顺序")
    @Test
    public void update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumber() throws Exception {
        prepareDataForResort();
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":3,\"procedureId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/procedures/1/cards/fooId2")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(3))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/1/cards/fooId2"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/procedures/1/cards/fooId2/assignments"));

        assertEquals(0, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId1'"));
        assertEquals(3, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId2'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId3'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId4'"));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId5'"));
    }

    @Scenario("当移动一个卡片时,卡片移动后的序号大于其前置序号,但在procedure中它移动后的序号并不是最大的。")
    @Test
    public void update_shouldResortSuccessfullyWhenCurrentOrderNumberMoreThanOriginNumberButNotTheBiggest() throws Exception {
        prepareDataForResort();
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":3,\"procedureId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/procedures/1/cards/fooId1")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(3))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/1/cards/fooId1"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/procedures/1/cards/fooId1/assignments"));

        assertEquals(3, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId1'"));
        assertEquals(0, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId2'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId3'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId4'"));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId5'"));
    }

    @Scenario("当一个卡片从某个procedure移动到另一个procedure时,不仅需要重新排序目标procedure,也要对原始procedure排序")
    @Test
    public void update_shouldResortSuccessfullyWhenCardIsFromAntherProcedure() throws Exception {
        prepareDataForResort();
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id,order_number) VALUES ('fooId6','this is the card summary.','play badminton',1,2,3)");
        given().body("{\"summary\":\"newSummary\",\"orderNumber\":3,\"procedureId\":1}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/procedures/1/cards/fooId6")
                .then()
                .statusCode(200)
                .body("summary", equalTo("newSummary"))
                .body("orderNumber", equalTo(3))
                .body("_links.self.href", equalTo("http://localhost:8007/procedures/1/cards/fooId6"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/1/cards"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/procedures/1/cards/fooId6/assignments"));

        assertEquals(0, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId1'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId2'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId3'"));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId4'"));
        assertEquals(5, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId5'"));
        assertEquals(3, jdbcTemplate.queryForInt("SELECT order_number FROM kb_card WHERE id='fooId6'"));
    }

    @Scenario("当更新一个卡片时,如果待更新的卡片不存在,则抛出资源不存在的错误")
    @Test
    public void update_shouldThrowResourceNotFoundExceptionWhenCardToUpdateIsNotExist() throws Exception {
        given().body("{\"summary\":\"newSummary\"}")
                .header("userName", userName)
                .contentType(ContentType.JSON)
                .when()
                .put("/procedures/1/cards/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("card[fooId] is not found."));
    }

    @Scenario("当删除一个卡片时,如果卡片存在,则删除成功")
    @Test
    public void delete_shouldDeleteSuccessfullyWhenTheCardIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES ('fooId','this is the card summary.','play badminton',1,1)");
        given().header("userName", userName)
                .when()
                .delete("/procedures/feeId/cards/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_card WHERE  delete_status=1").size());
    }

    @Scenario("当删除一个卡片时,如果待删除的卡片不存在,则抛出404错误")
    @Test
    public void delete_shouldDeleteFailedWhenTheCardIsNotExist() {
        given().header("userName", userName)
                .when()
                .delete("/procedures/feeId/cards/non-exists-cardId")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("card[non-exists-cardId] is not found."));
    }
}
