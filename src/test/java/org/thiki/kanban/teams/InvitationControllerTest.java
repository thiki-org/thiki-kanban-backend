package org.thiki.kanban.teams;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.teams.invitation.InvitationCodes;
import org.thiki.kanban.teams.invitation.InvitationService;

import javax.annotation.Resource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Created by xutao on 9/12/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class InvitationControllerTest extends TestBase {

    @Resource
    private InvitationService invitationService;

    @Scenario("用户可以通过用户名邀请其他成员加入到团队中")
    @Test
    public void inviteOthersToJoinTeam() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,author) VALUES ('foo-team-Id','team-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_team_members (id,team_id,member,author) VALUES ('foo-team-member-id','foo-team-Id','someone','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','someone','password')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('another-user-id','766191920@qq.com','another-user','password')");

        given().header("userName", userName)
                .body("{\"invitee\":\"another-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/invitation")
                .then()
                .statusCode(201)
                .body("invitee", equalTo("another-user"))
                .body("_links.self.href", equalTo("http://localhost:8007/teams/foo-team-Id/invitation"))
                .body("_links.members.href", equalTo("http://localhost:8007/teams/foo-team-Id/members"));
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_team_member_invitation where team_id='fooId' AND invitee='another-user'").size());
    }

    @Scenario("如果邀请加入的团队并不存在，则不允许发送邀请")
    @Test
    public void NotAllowedIfTeamIsNotExist() throws Exception {
        given().header("userName", userName)
                .body("{\"invitee\":\"another-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/invitation")
                .then()
                .statusCode(400)
                .body("code", equalTo(InvitationCodes.TEAM_IS_NOT_EXISTS.code()))
                .body("message", equalTo(InvitationCodes.TEAM_IS_NOT_EXISTS.message()));
    }


}
