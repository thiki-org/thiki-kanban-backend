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
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by xubt on 5/18/16.
 */
@Domain(order = DomainOrder.BOARD, name = "看板")
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardAuthenticationTest extends AuthenticationTestBase {
    @Scenario("看板权限管控>当用户为看板所属团队成员时,但并非团队看板,则只允许读取,不允许其他操作")
    @Test
    public void allowReadOnlyIfTheUserIsNotTheTeamAndTheBoardOwner() throws Exception {
        dbPreparation.table("kb_board").names("id,name,author,owner,project_id").values("fooId", "board-name", "others", "others", "projectId-foo").exec();
        dbPreparation.table("kb_project_members").names("id,project_id,member").values("fooId", "projectId-foo", "someone").exec();

        given().header("userName", "someone").log().all()
                .when()
                .get("/someone/boards/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("board-name"))
                .body("author", equalTo("others"))
                .body("_links.all.href", endsWith("/someone/boards"))
                .body("_links.stages.href", endsWith("/boards/fooId/stages"))

                .body("_links.self.href", endsWith("/someone/boards/fooId"))
                .body("_links.self.actions.read.isAllowed", equalTo(true))
                .body("_links.self.actions.modify.isAllowed", equalTo(true))
                .body("_links.self.actions.delete.isAllowed", equalTo(true));
    }
}
