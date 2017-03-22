package org.thiki.kanban.assignment;

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
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by xubt on 6/16/16.
 */
@Domain(order = DomainOrder.ASSIGNMENT, name = "任务认领")
@RunWith(SpringJUnit4ClassRunner.class)
public class AssignmentControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,user_name,password) " +
                "VALUES ('assignee-id','766191920@qq.com','assigneeId','password')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,user_name,password) " +
                "VALUES ('assigner-id','assigner@gmail.com','assignerId','password')");
        dbPreparation.table("kb_user_profile")
                .names("id,user_name")
                .values("assignerId-profile-id", "assignerId").exec();
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author) VALUES ('boardId-foo','board-name','someone')");
    }

    @Scenario("成功给指定卡片增加一条分配记录")
    @Test
    public void assign_shouldReturn201WhenAssigningSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('stage-fooId','this is the first stage.','tao','feeId',1,1)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,code,content,author,stage_id) VALUES ('card-fooId','this is the card summary.','0000000','play badminton','someone','stage-fooId')");
        given().header("userName", "someone")
                .body("[{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}]")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/assignments")
                .then()
                .statusCode(200)
                .body("assignments[0].id", equalTo("fooId"))
                .body("assignments[0].assignee", equalTo("assigneeId"))
                .body("assignments[0].assigner", equalTo("assignerId"))
                .body("assignments[0].author", equalTo("someone"))
                .body("assignments[0]._links.card.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId"))
                .body("assignments[0]._links.self.href", endsWith("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/assignments"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_activity where card_id='card-fooId'").size());
    }

    @Scenario("当用户根据cardID获取分配记录时,如果指定的卡片存在,则返回分配记录集合")
    @Test
    public void findByCardId_shouldReturnAssignmentsSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('cardId-foo','this is the card summary.','play badminton',1,'fooId')");
        jdbcTemplate.execute("INSERT INTO  kb_card_assignment (id,card_id,assignee,assigner,author) VALUES ('fooId','cardId-foo','assigneeId-foo','assignerId-foo','authorId-foo')");
        given().header("userName", "authorId-foo")
                .contentType(ContentType.JSON)
                .when()
                .get("/boards/boardId-foo/stages/1/cards/cardId-foo/assignments")
                .then()
                .statusCode(200)
                .body("assignments[0].id", equalTo("fooId"))
                .body("assignments[0].assignee", equalTo("assigneeId-foo"))
                .body("assignments[0].assigner", equalTo("assignerId-foo"))
                .body("assignments[0].author", equalTo("authorId-foo"))
                .body("assignments[0]._links.card.href", endsWith("/boards/boardId-foo/stages/1/cards/cardId-foo"))
                .body("assignments[0]._links.self.href", endsWith("/boards/boardId-foo/stages/1/cards/cardId-foo/assignments"))
                .body("assignments[0]._links.assigneeProfile.href", endsWith("/users/assigneeId-foo/profile"))
                .body("assignments[0]._links.assigneeAvatar.href", endsWith("/users/assigneeId-foo/avatar"))
                .body("_links.self.href", endsWith("/boards/boardId-foo/stages/1/cards/cardId-foo/assignments"))
                .body("_links.card.href", endsWith("/boards/boardId-foo/stages/1/cards/cardId-foo"));
    }

    @Scenario("当卡片已经归档时，不允许再进行分配操作")
    @Test
    public void should_not_allowed_if_card_is_archived() {
        jdbcTemplate.execute("INSERT INTO  kb_stage (id,title,author,board_id,type,status) VALUES ('stage-fooId','this is the first stage.','tao','feeId',9,9)");
        jdbcTemplate.execute("INSERT INTO  kb_card (id,summary,content,author,stage_id) VALUES ('card-fooId','this is the card summary.','play badminton','someone','stage-fooId')");
        jdbcTemplate.execute("INSERT INTO  kb_card_assignment (id,card_id,assignee,assigner,author) VALUES ('fooId','cardId-foo','assigneeId-foo','assignerId-foo','someone')");
        given().header("userName", "authorId-foo")
                .body("[{\"assignee\":\"assigneeId\",\"assigner\":\"assignerId\"}]")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/boardId-foo/stages/stage-fooId/cards/card-fooId/assignments")
                .then()
                .statusCode(400)
                .body("code", equalTo(AssignmentCodes.CARD_IS_ALREADY_ARCHIVED.code()))
                .body("message", equalTo(AssignmentCodes.CARD_IS_ALREADY_ARCHIVED.message()));
    }
}
