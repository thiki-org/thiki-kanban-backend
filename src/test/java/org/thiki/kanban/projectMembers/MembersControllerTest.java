package org.thiki.kanban.projectMembers;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;
import org.thiki.kanban.projects.project.ProjectCodes;
import org.thiki.kanban.projects.projectMembers.ProjectMembersCodes;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Created by 濤 on 7/26/16.
 */
@Domain(order = DomainOrder.PROJECT_MEMBER, name = "项目成员")
@RunWith(SpringJUnit4ClassRunner.class)
public class MembersControllerTest extends TestBase {
    @Scenario("加入一个项目")
    @Test
    public void joinProject_shouldReturn201WhenJoinProjectSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('foo-projectId','project-name','someone')");
        given().header("userName", "someone")
                .body("{\"member\":\"someone\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/projects/foo-projectId/members")
                .then()
                .statusCode(201)
                .body("projectId", equalTo("foo-projectId"))
                .body("member", equalTo("someone"))
                .body("id", equalTo("fooId"))
                .body("_links.self.href", endsWith("/projects/foo-projectId/members"));
    }

    @Scenario("退出项目>用户加入某个项目后,可以选择离开项目")
    @Test
    public void leaveProject() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('foo-projectId','project-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_project_members (id,project_id,member,author) VALUES ('foo-project-member-id','foo-projectId','someone','someone')");
        given().header("userName", "someone")
                .when()
                .delete("/projects/foo-projectId/members/someone")
                .then()
                .statusCode(200)
                .body("_links.projects.href", endsWith("/someone/projects"))
                .body("_links.self.href", endsWith("/projects/foo-projectId/members/someone"));
    }

    @Scenario("加入项目时,如果该项目并不存在,则不允许加入")
    @Test
    public void joinProject_shouldReturnFailedIfProjectIsNotExist() throws Exception {
        given().header("userName", "someone")
                .body("{\"member\":\"someone\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/projects/foo-projectId/members")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.code()))
                .body("message", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.message()));
    }

    @Scenario("加入项目时,如果待加入的成员已经在项目中,则不允许加入")
    @Test
    public void joinProject_shouldReturnFailedIfMemberIsAlreadyIn() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('foo-projectId','project-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_project_members (id,project_id,member,author) VALUES ('foo-project-member-id','foo-projectId','someone','someone')");
        given().header("userName", "someone")
                .body("{\"member\":\"someone\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/projects/foo-projectId/members")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Member named someone is already in the projects."));
    }

    @Scenario("当用户加入一个项目后，可以获取该项目的所有成员")
    @Test
    public void loadProjectMembersByProjectId() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_user_profile (id,user_name,nick_name) VALUES ('foo-profile-id','someone','tao')");
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('foo-projectId','project-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_project_members (id,project_id,member,author) VALUES ('foo-project-member-id','foo-projectId','someone','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','someone@gmail.com','someone','password')");

        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .when()
                .get("/projects/foo-projectId/members")
                .then()
                .statusCode(200)
                .body("members[0].userName", equalTo("someone"))
                .body("members[0].email", equalTo("someone@gmail.com"))
                .body("members[0].profile.nickName", equalTo("tao"))
                .body("members[0]._links.self.href", endsWith("/projects/foo-projectId/members/someone"))
                .body("members[0]._links.avatar.href", endsWith("/users/someone/avatar"))
                .body("members[0]._links.profile.href", endsWith("/users/someone/profile"))
                .body("_links.invitation.href", endsWith("/projects/foo-projectId/members/invitation"))
                .body("_links.member.href", endsWith("/projects/foo-projectId/members/someone"));
    }

    @Scenario("当用户加入一个项目后，可以获取该项目的所有成员。但是当项目不存在时,则不允许获取。")
    @Test
    public void NotAllowedIfProjectIsNotExitsWhenLoadingProjectMembersByProjectId() throws Exception {
        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .when()
                .get("/projects/foo-projectId/members")
                .then()
                .statusCode(400)
                .body("code", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.code()))
                .body("message", equalTo(ProjectCodes.PROJECT_IS_NOT_EXISTS.message()));
    }

    @Scenario("若当前用户并非项目成员，则不允许获取")
    @Test
    public void NotAllowedIfCurrentUserIsNotAMemberOfTheProjectWhenLoadingProjectMembersByProjectId() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_project (id,name,author) VALUES ('foo-projectId','project-name','someone')");
        given().header("userName", "someone")
                .contentType(ContentType.JSON)
                .when()
                .get("/projects/foo-projectId/members")
                .then()
                .statusCode(401)
                .body("code", equalTo(ProjectMembersCodes.CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_PROJECT.code()))
                .body("message", equalTo(ProjectMembersCodes.CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_PROJECT.message()));
    }
}
