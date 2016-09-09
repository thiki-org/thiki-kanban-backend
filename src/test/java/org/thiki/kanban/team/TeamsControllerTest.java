package org.thiki.kanban.team;

import com.jayway.restassured.http.ContentType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Created by bogehu on 7/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamsControllerTest extends TestBase {
    @Scenario("创建一个团队")
    @Test
    public void create_shouldReturn201WhenCreateTeamSuccessfully() throws Exception {
        given().header("userName", userName)
                .body("{\"name\":\"思奇团队讨论组\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/teams")
                .then()
                .statusCode(201)
                .body("name", equalTo("思奇团队讨论组"))
                .body("id", equalTo("fooId"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/teams/fooId"))
                .body("_links.boards.href", equalTo("http://localhost:8007/teams/fooId/boards"));
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_team_members where team_id='fooId' AND member='someone'").size());
    }

    @Scenario("创建团队时，如果团队名称为空，则不允许创建")
    @Test
    public void creationIsNotAllowedIfTeamNameIsEmpty() throws Exception {
        given().header("userName", userName)
                .body("{\"name\":\"\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/teams")
                .then()
                .statusCode(400)
                .body("message", equalTo(TeamsCodes.nameIsRequired));
    }

    @Scenario("创建团队时，如果未提供团队名称，则不允许创建")
    @Test
    public void creationIsNotAllowedIfTeamNameIsNull() throws Exception {
        given().header("userName", userName)
                .body("{}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/teams")
                .then()
                .statusCode(400)
                .body("message", equalTo(TeamsCodes.nameIsRequired));
    }


    @Scenario("创建团队时，如果团队名称超限，则不允许创建")
    @Test
    public void creationIsNotAllowedIfTeamNameIsTooLong() throws Exception {
        given().header("userName", userName)
                .body("{\"name\":\"团队名称团队名称团队名称团队名称团队名称团队名称团队名称\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/teams")
                .then()
                .statusCode(400)
                .body("message", equalTo(TeamsCodes.nameIsInvalid));
    }

    @Scenario("根据用户名获取其所在团队")
    @Test
    public void loadTheTeamsWhichTheUserIsIn() {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,reporter) VALUES ('fooId','team-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_team_members (id,team_id,member,reporter) VALUES ('foo-team-member-id','fooId','someone','someone')");

        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .when()
                .get("/someone/teams")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo("fooId"))
                .body("[0].name", equalTo("team-name"))
                .body("[0].reporter", equalTo("someone"))
                .body("[0]._links.self.href", equalTo("http://localhost:8007/someone/teams/fooId"))
                .body("[0]._links.boards.href", equalTo("http://localhost:8007/teams/fooId/boards"));
    }

    @Ignore
    @Scenario("用户根据ID获取team时,如果该team存在,则返回其信息")
    @Test
    public void shouldReturnBoardWhenTeamIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,reporter) VALUES ('fooId','team-name','someone')");
        given().header("userName", "someone")
                .when()
                .get("/teams/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("team-name"))
                .body("reporter", equalTo("someone"))
                .body("_links.self.href", equalTo("http://localhost:8007/teams/fooId"))
                .body("_links.boards.href", equalTo("http://localhost:8007/teams/fooId/boards"));
    }

    @Ignore
    @Scenario("更新一个team信息")
    @Test
    public void shouldUpdateSuccessfully() {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,reporter) VALUES ('fooId','team-name','someone')");
        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"new-name\"}")
                .when()
                .put("/teams/fooId")
                .then()
                .statusCode(200)
                .body("name", equalTo("new-name"))
                .body("reporter", equalTo("someone"))
                .body("_links.self.href", equalTo("http://localhost:8007/teams/fooId"));
        assertEquals("new-name", jdbcTemplate.queryForObject("select name from kb_team where id='fooId'", String.class));
    }
}
