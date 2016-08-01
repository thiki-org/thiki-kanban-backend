package org.thiki.kanban.entry;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 5/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EntriesControllerTest extends TestBase {
    @Scenario("创建一个新的entry后,返回自身及links信息")
    @Test
    public void shouldReturn201WhenCreateEntrySuccessfully() {
        given().header("userId", "11222")
                .body("{\"title\":\"this is the entry title.\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the entry title."))
                .body("reporter", equalTo(11222))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.cards.href", equalTo("http://localhost:8007/entries/fooId/cards"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"));
    }

    @Scenario("创建一个新的entry,如果它并不是指定boardId下第一个entry,则其排序号应根据当前entry数量自动增加")
    @Test
    public void create_orderNumberShouldAutoIncrease() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('existedFooId','this is the first entry.',1,'feeId')");
        given().header("userId", "11222")
                .body("{\"title\":\"this is the entry title.\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the entry title."))
                .body("reporter", equalTo(11222))
                .body("creationTime", notNullValue())
                .body("orderNumber", equalTo(1))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.cards.href", equalTo("http://localhost:8007/entries/fooId/cards"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"));
    }

    @Scenario("创建新的entry时,如果名称为空,则不允许创建并返回客户端400错误")
    @Test
    public void shouldFailedIfEntryTitleIsEmpty() {
        given().header("userId", "11222")
                .body("{\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("列表名称不能为空"));
    }

    @Scenario("创建新的entry时,如果boardID为空,则不允许创建并返回客户端400错误")
    @Test
    public void shouldFailedIfEntryBoardIdIsEmpty() {
        given().header("userId", "11222")
                .body("{\"title\":\"this is the entry title.\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("boardId不能为空"));
    }

    @Scenario("创建新的entry时,如果名称长度不在合法的范围,则不允许创建并返回客户端400错误")
    @Test
    public void shouldReturnBadRequestWhenEntryTitleIsTooLong() {
        given().header("userId", "11222")
                .body("{\"boardId\":\"feeId\"}}")
                .body("{\"title\":\"\",\"boardId\":\"feeId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/entries")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("列表名称长度非法,有效长度为1~50个字符。"));
    }

    @Scenario("当根据entryId查找entry时,如果entry存在,则将其返回")
    @Test
    public void shouldReturnEntryWhenFindEntryById() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        given().header("userId", "11222")
                .when()
                .get("/boards/feeId/entries/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("title", equalTo("this is the first entry."))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"));
    }

    @Scenario("更新entry时,如果参数合法且待更新的entry存在,则更新成功")
    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/entries/fooId")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"));
        assertEquals("newTitle", jdbcTemplate.queryForObject("select title from kb_entry where id='fooId'", String.class));
    }

    @Scenario("更新entry时,如果参数合法但待更新的entry不存在,则更新失败")
    @Test
    public void update_shouldFailedWhenTheEntryToUpdateIsNotExists() {
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}")
                .when()
                .put("/boards/feeId/entries/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("entry[fooId] is not found."));
    }

    @Scenario("当移动一个entry时,移动后的排序小于其原先的排序")
    @Test
    public void update_shouldResortSuccessfullyWhenCurrentSortNumberIsLessThanOriginNumber() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId1','this is the first entry.',1,'feeId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId2','this is the first entry.',1,'feeId',1)");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"0\"}}")
                .when()
                .put("/boards/feeId/entries/fooId2")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId2"));
        assertEquals("1", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId1'", String.class));
        assertEquals("0", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId2'", String.class));
    }

    @Scenario("当移动一个entry时,移动后的排序大于其原先的排序")
    @Test
    public void update_shouldResortSuccessfullyWhenCurrentSortNumberIsMoreThanOriginNumber() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId1','this is the first entry.',1,'feeId',0)");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId2','this is the first entry.',1,'feeId',1)");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id,order_number) VALUES ('fooId3','this is the first entry.',1,'feeId',2)");
        given().header("userId", "11222")
                .contentType(ContentType.JSON)
                .body("{\"title\":\"newTitle\",\"boardId\":\"feeId\",\"orderNumber\":\"2\"}}")
                .when()
                .put("/boards/feeId/entries/fooId1")
                .then()
                .statusCode(200)
                .body("title", equalTo("newTitle"))
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId1"));
        assertEquals("2", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId1'", String.class));
        assertEquals("0", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId2'", String.class));
        assertEquals("1", jdbcTemplate.queryForObject("select order_number from kb_entry where id='fooId3'", String.class));
    }

    @Scenario("当删除一个entry时,如果待删除的entry存在,则删除成功")
    @Test
    public void shouldDeleteSuccessfullyWhenTheEntryIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        given().header("userId", "11222")
                .when()
                .delete("/boards/feeId/entries/fooId")
                .then()
                .statusCode(200);
        assertEquals(1, jdbcTemplate.queryForList("select * FROM kb_entry WHERE  delete_status=1").size());
    }

    @Scenario("当删除一个entry时,如果待删除的entry不存在,则删除成功并返回客户端错误")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenEntryToDeleteIsNotExist() throws Exception {
        given().header("userId", "11222")
                .when()
                .delete("/boards/feeId/entries/fooId")
                .then()
                .statusCode(404)
                .body("message", equalTo("entry[fooId] is not found."));
    }

    @Scenario("通过boardId获取所有的entry")
    @Test
    public void shouldReturnAllEntriesSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('fooId','this is the first entry.',1,'feeId')");
        jdbcTemplate.execute("INSERT INTO  kb_entry (id,title,reporter,board_id) VALUES ('randomId','this is the first entry.',1,'feeId2')");
        given().header("userId", "11222")
                .when()
                .get("/boards/feeId/entries")
                .then()
                .statusCode(200)
                .body("[0].title", equalTo("this is the first entry."))
                .body("[0].reporter", equalTo(1))
                .body("[0].creationTime", notNullValue())
                .body("[0]._links.all.href", equalTo("http://localhost:8007/boards/feeId/entries"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/boards/feeId/entries/fooId"))
                .body("[0]._links.cards.href", equalTo("http://localhost:8007/entries/fooId/cards"));
    }
}
