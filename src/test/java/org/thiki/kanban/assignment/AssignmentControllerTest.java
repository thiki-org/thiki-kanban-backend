package org.thiki.kanban.assignment;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.card.CardsCodes;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 6/16/16.
 */
@Domain(order = DomainOrder.ASSIGNMENT, name = "任务认领")
@RunWith(SpringJUnit4ClassRunner.class)
public class AssignmentControllerTest extends TestBase {

    @Scenario("成功创建一条分配记录")
    @Test
    public void assign_shouldReturn201WhenAssigningSuccessfully() {
        given().header("userName", "someone")
                .body("{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/1/cards/fooId/assignments")
                .then()
                .statusCode(201)
                .body("id", equalTo("fooId"))
                .body("assignee", equalTo("assigneeId"))
                .body("assigner", equalTo("assignerId"))
                .body("author", equalTo("someone"))
                .body("_links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId/assignments"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId/assignments/fooId"));
    }

    @Scenario("当用户根据ID查找分配记录时,如果该记录存在则将其返回")
    @Test
    public void findById_shouldReturnAssignmentSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,name,email,password) VALUES ('assigneeId-foo','徐濤','766191920@qq.com','password')");
        jdbcTemplate.execute("INSERT INTO  kb_card_assignment (id,card_id,assignee,assigner,author) VALUES ('fooId','cardId-foo','assigneeId-foo','assignerId-foo','authorId-foo')");
        given().header("userName", "authorId-foo")
                .when()
                .get("/boards/boardId-foo/procedures/1/cards/fooId/assignments/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("assignee", equalTo("assigneeId-foo"))
                .body("assigner", equalTo("assignerId-foo"))
                .body("name", equalTo("徐濤"))
                .body("author", equalTo("authorId-foo"))
                .body("_links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId/assignments"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId/assignments/fooId"));
    }

    @Scenario("当用户根据cardID获取分配记录时,如果指定的卡片存在,则返回分配记录集合")
    @Test
    public void findByCardId_shouldReturnAssignmentsSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,name,email,password) VALUES ('assigneeId-foo','徐濤','766191920@qq.com','password')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES ('cardId-foo','this is the card summary.','play badminton',1,'fooId')");
        jdbcTemplate.execute("INSERT INTO  kb_card_assignment (id,card_id,assignee,assigner,author) VALUES ('fooId','cardId-foo','assigneeId-foo','assignerId-foo','authorId-foo')");
        given().header("userName", "authorId-foo")
                .body("{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}")
                .contentType(ContentType.JSON)
                .when()
                .get("/boards/boardId-foo/procedures/1/cards/cardId-foo/assignments")
                .then()
                .statusCode(200)
                .body("assignments[0].id", equalTo("fooId"))
                .body("assignments[0].assignee", equalTo("assigneeId-foo"))
                .body("assignments[0].assigner", equalTo("assignerId-foo"))
                .body("assignments[0].name", equalTo("徐濤"))
                .body("assignments[0].author", equalTo("authorId-foo"))
                .body("assignments[0]._links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/cardId-foo"))
                .body("assignments[0]._links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/cardId-foo/assignments"))
                .body("assignments[0]._links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/cardId-foo/assignments/fooId"))
                .body("assignments[0]._links.assigneeProfile.href", equalTo("http://localhost:8007/users/assigneeId-foo/profile"))
                .body("assignments[0]._links.assigneeAvatar.href", equalTo("http://localhost:8007/users/assigneeId-foo/avatar"))
                .body("_links.self.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/cardId-foo/assignments"))
                .body("_links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/cardId-foo"));
    }

    @Scenario("任务认领>当用户此前已经认领该任务后,则不可以再次认领")
    @Test
    public void notAllowedIfAlreadyAssigned() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,name,email,password) VALUES ('assigneeId-foo','徐濤','766191920@qq.com','password')");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,procedure_id) VALUES ('cardId-foo','this is the card summary.','play badminton',1,'fooId')");
        jdbcTemplate.execute("INSERT INTO  kb_card_assignment (id,card_id,assignee,assigner,author) VALUES ('fooId','cardId-foo','assigneeId-foo','assignerId-foo','authorId-foo')");
        given().header("userName", "authorId-foo")
                .body("{\"assignee\":\"assigneeId-foo\",\"assigner\":\"assignerId\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/procedures/1/cards/cardId-foo/assignments")
                .then()
                .statusCode(400)
                .body("code", equalTo(AssignmentCodes.ALREADY_ASSIGNED.code()))
                .body("message", equalTo(AssignmentCodes.ALREADY_ASSIGNED.message()));
    }

    @Scenario("当用户根据cardID获取分配记录时,如果指定的卡片并不存在,则返回404客户端错误")
    @Test
    public void findByCardId_shouldReturnErrorWhenCardIsNotExist() {
        jdbcTemplate.execute("INSERT INTO  kb_card_assignment (id,card_id,assignee,assigner,author) VALUES ('fooId','cardId-foo','assigneeId-foo','assignerId-foo','authorId-foo')");
        given().header("userName", "authorId-foo")
                .body("{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}")
                .contentType(ContentType.JSON)
                .when()
                .get("/boards/boardId-foo/procedures/1/cards/cardId-foo/assignments")
                .then()
                .statusCode(400)
                .body("code", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.code()))
                .body("message", equalTo(CardsCodes.CARD_IS_NOT_EXISTS.message()));
    }

    @Scenario("当用户想取消某个分配时,如果指定的分配记录存在,则成功将其取消")
    @Test
    public void delete_shouldReturnSuccessfully() {
        dbPreparation.table("kb_card_assignment")
                .names("id,card_id,assignee,assigner,author")
                .values("'fooId','cardId-foo','assigneeId-foo','assignerId-foo','authorId-foo'").exec();
        given().header("userName", "authorId-foo")
                .when()
                .delete("/boards/boardId-foo/procedures/1/cards/fooId/assignments/fooId")
                .then()
                .statusCode(200)
                .body("_links.card.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId"))
                .body("_links.assignments.href", equalTo("http://localhost:8007/boards/boardId-foo/procedures/1/cards/fooId/assignments"));
    }

    @Scenario("当用户想取消某个分配时,如果指定的分配记录并不存在,则返回404客户端错误")
    @Test
    public void delete_shouldReturnErrorWhenAssignmentIsNotExist() {
        given().header("userName", "authorId-foo")
                .when()
                .delete("/boards/boardId-foo/procedures/1/cards/fooId/assignments/fooId")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("assignment[fooId] is not found."));
    }
}
