package org.thiki.kanban.projects;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.projects.project.ProjectCodes;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertEquals;

/**
 * Created by bogehu on 7/11/16.
 */

@Domain(order = DomainOrder.PROJECT, name = "团队")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectControllerTest extends TestBase {
    @Scenario("创建一个团队")
    @Test
    public void create_shouldReturn201WhenCreateTeamSuccessfully() throws Exception {
        given().header("userName", userName)
                .body("{\"name\":\"思奇团队讨论组\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/projects")
                .then()
                .statusCode(201)
                .body("name", equalTo("思奇团队讨论组"))
                .body("id", equalTo("fooId"))
                .body("_links.self.href", endsWith("/projects/fooId"));
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_project_members where project_id='fooId' AND member='someone'").size());
    }

    @Scenario("创建团队时，如果团队名称为空，则不允许创建")
    @Test
    public void creationIsNotAllowedIfTeamNameIsEmpty() throws Exception {
        given().header("userName", userName)
                .body("{\"name\":\"\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/projects")
                .then()
                .statusCode(400)
                .body("message", equalTo(ProjectCodes.nameIsRequired));
    }

    @Scenario("创建团队时，如果未提供团队名称，则不允许创建")
    @Test
    public void creationIsNotAllowedIfTeamNameIsNull() throws Exception {
        given().header("userName", userName)
                .body("{}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/projects")
                .then()
                .statusCode(400)
                .body("message", equalTo(ProjectCodes.nameIsRequired));
    }

    @Scenario("创建团队时，如果在本人名下已经存在相同名称的团队，则不允许创建")
    @Test
    public void creationIsNotAllowedIfTeamNameIsConflict() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('fooId','project-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_project_members (id,project_id,member,author) VALUES ('foo-project-member-id','fooId','someone','someone')");

        given().header("userName", userName)
                .body("{\"name\":\"project-name\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/projects")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProjectCodes.PROJECT_IS_ALREADY_EXISTS.code()))
                .body("message", equalTo(ProjectCodes.PROJECT_IS_ALREADY_EXISTS.message()));
    }

    @Scenario("创建团队时，如果团队名称超限，则不允许创建")
    @Test
    public void creationIsNotAllowedIfTeamNameIsTooLong() throws Exception {
        given().header("userName", userName)
                .body("{\"name\":\"团队名称团队名称团队名称团队名称团队名称团队名称团队名称\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/someone/projects")
                .then()
                .statusCode(400)
                .body("message", equalTo(ProjectCodes.nameIsInvalid));
    }

    @Scenario("根据用户名获取其所在团队")
    @Test
    public void loadTheTeamsWhichTheUserIsIn() {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('fooId','project-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_project_members (id,project_id,member,author) VALUES ('foo-project-member-id','fooId','someone','someone')");

        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .when()
                .get("/someone/projects")
                .then()
                .statusCode(200)
                .body("projects[0].id", equalTo("fooId"))
                .body("projects[0].name", equalTo("project-name"))
                .body("projects[0].author", equalTo("someone"))
                .body("projects[0]._links.self.href", endsWith("/projects/fooId"));
    }

    @Scenario("用户根据ID获取project时,如果该project存在,则返回其信息")
    @Test
    public void shouldReturnBoardWhenTeamIsExist() {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('fooId','project-name','someone')");
        given().header("userName", "someone")
                .when()
                .get("/projects/fooId")
                .then()
                .statusCode(200)
                .body("id", equalTo("fooId"))
                .body("name", equalTo("project-name"))
                .body("author", equalTo("someone"))
                .body("_links.self.href", endsWith("/projects/fooId"))
                .body("_links.members.href", endsWith("/projects/fooId/members"));
    }

    @Scenario("获取指定团队信息>用户根据ID获取project时,如果该project不存在,则告知客户端错误")
    @Test
    public void shouldNoticeClientIfTeamIsNotExist() throws Exception {
        given().header("userName", "someone")
                .when()
                .get("/projects/fooId")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.code()))
                .body("message", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.message()));
    }

    @Scenario("更新团队信息>用户创建一个团队后,可以更新该团队的信息")
    @Test
    public void updateTeam() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('projectId-foo','project-name','someone')");
        given().header("userName", userName)
                .body("{\"name\":\"new-name\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/projectId-foo")
                .then()
                .statusCode(200)
                .body("name", equalTo("new-name"))
                .body("id", equalTo("projectId-foo"))
                .body("_links.self.href", endsWith("/projects/projectId-foo"));
        assertEquals("new-name", jdbcTemplate.queryForObject("select NAME from kb_project where id='projectId-foo'", String.class));
    }

    @Scenario("更新团队信息>当团队不存在时,不允许更新")
    @Test
    public void notAllowedIfTeamIsNotExist() throws Exception {
        given().header("userName", userName)
                .body("{\"name\":\"new-name\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/projects/projectId-foo")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.code()))
                .body("message", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.message()));
    }
}
