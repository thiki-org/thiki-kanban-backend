package org.thiki.kanban.teamMembers;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by 濤 on 7/26/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamMembersControllerTest extends TestBase {
    @Scenario("加入一个团队")
    @Test
    public void join_shouldReturn201WhenJoinTeamSuccessfully() throws Exception {
        given().header("userName", "someone")
                .body("{\"member\":\"someone\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-teamId/teamMembers")
                .then()
                .statusCode(201)
                .body("teamId", equalTo("foo-teamId"))
                .body("member", equalTo("someone"))
                .body("id", equalTo("fooId"))
                .body("_links.self.href", equalTo("http://localhost:8007/teams/foo-teamId/teamMembers"));
    }
}
