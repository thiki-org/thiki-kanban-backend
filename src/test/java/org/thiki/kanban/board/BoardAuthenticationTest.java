package org.thiki.kanban.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.AuthenticationTestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by xubt on 5/18/16.
 */
@Domain(order = DomainOrder.BOARD, name = "看板")
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardAuthenticationTest extends AuthenticationTestBase {
    @Scenario("看板权限管控>当用户删除一个指定的看板时,如果该用户并非看板所属团队的成员,且看板非个人所属,则不允许删除")
    @Test
    public void notAllowedIfCurrentHasNoAuthority() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author,owner) VALUES ('fooId','board-name','someone','others')");
        given().header("userName", "someone")
                .when()
                .delete("/someone/boards/fooId")
                .then()
                .statusCode(401)
                .body("code", equalTo(BoardCodes.FORBID_CURRENT_IS_NOT_A_MEMBER_OF_THE_TEAM.code()))
                .body("message", equalTo(BoardCodes.FORBID_CURRENT_IS_NOT_A_MEMBER_OF_THE_TEAM.message()));
    }

    @Scenario("看板权限管控>当用户为看板所属团队成员时,但并非团队看板,则只允许读取,不允许其他操作")
    @Test
    public void allowReadOnlyIfTheUserIsNotTheTeamAndTheBoardOwner() throws Exception {
        dbPreparation.table("kb_board").names("id,name,author,owner,team_id").values("fooId", "board-name", "others", "others", "teamId-foo").exec();
        dbPreparation.table("kb_team_members").names("id,team_id,member").values("fooId", "teamId-foo", "someone").exec();

        given().header("userName", "someone").log().all()
                .when()
                .get("/someone/boards/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("author", equalTo("others"))
                .body("_links.all.href", endsWith("/someone/boards"))
                .body("_links.procedures.href", endsWith("/boards/fooId/procedures"))

                .body("_links.self.href", endsWith("/someone/boards/fooId"))
                .body("_links.self.actions.assign", nullValue())
                .body("_links.self.actions.read.isAllowed", equalTo(true))
                .body("_links.self.actions.modify", nullValue())
                .body("_links.self.actions.delete", nullValue());
    }
}
