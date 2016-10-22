package org.thiki.kanban.procedure;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.teams.teamMembers.TeamMembersCodes;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by xubt on 9/21/16.
 */
@Domain(order = DomainOrder.PROCEDURE, name = "工序")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProceduresAuthenticationTest extends TestBase {
    private String userName = "fooName";

    @Scenario("不允许创建,由于当前用户并非团队成员,因此不应允许其创建工序")
    @Test
    public void notAllowedIfCurrentIsNotTheMemberOfTheTeam() {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author) VALUES ('fooId','board-name','someone')");
        given().header("userName", userName)
                .body("{\"title\":\"this is the procedure title.\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/fooId/procedures")
                .then()
                .statusCode(400)
                .body("code", equalTo(TeamMembersCodes.CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_TEAM.code()))
                .body("message", equalTo(TeamMembersCodes.CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_TEAM.message()));
    }

    @Scenario("创建一个新的procedure后,返回自身及links信息")
    @Test
    public void shouldReturn201WhenCreateProcedureSuccessfully() {
        given().header("userName", userName)
                .body("{\"title\":\"this is the procedure title.\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/boards/feeId/procedures")
                .then()
                .statusCode(201)
                .body("title", equalTo("this is the procedure title."))
                .body("author", equalTo(userName))
                .body("creationTime", notNullValue())
                .body("_links.all.href", equalTo("http://localhost:8007/boards/feeId/procedures"))
                .body("_links.cards.href", equalTo("http://localhost:8007/procedures/fooId/cards"))
                .body("_links.self.href.post", equalTo("http://localhost:8007/boards/feeId/procedures/fooId"))
                .body("_links.self.methods.get", equalTo(true))
                .body("_links.self.methods.post", equalTo(true))
                .body("_links.self.methods.put", equalTo(true))
                .body("_links.self.methods.delete", equalTo(true));
    }
}
