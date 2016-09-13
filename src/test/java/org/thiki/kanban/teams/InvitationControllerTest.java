package org.thiki.kanban.teams;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.teams.invitation.InvitationCodes;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Created by xutao on 9/12/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class InvitationControllerTest extends TestBase {

    @Scenario("用户可以通过用户名邀请其他成员加入到团队中")
    @Test
    public void inviteOthersToJoinTeam() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,author) VALUES ('foo-team-Id','team-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_team_members (id,team_id,member,author) VALUES ('foo-team-member-id','foo-team-Id','someone','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','someone','password')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('invitee-user-id','766191920@qq.com','invitee-user','password')");

        given().header("userName", userName)
                .body("{\"invitee\":\"invitee-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/members/invitation")
                .then()
                .statusCode(201)
                .body("invitee", equalTo("invitee-user"))
                .body("_links.self.href", equalTo("http://localhost:8007/teams/foo-team-Id/members/invitation"))
                .body("_links.members.href", equalTo("http://localhost:8007/teams/foo-team-Id/members"));
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_team_member_invitation where team_id='fooId' AND invitee='invitee-user'").size());
    }

    @Scenario("如果邀请人为空，怎不允许发送邀请")
    @Test
    public void NotAllowedIfInviteeIsEmpty() throws Exception {
        given().header("userName", userName)
                .body("{\"invitee\":\"\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/members/invitation")
                .then()
                .statusCode(400)
                .body("message", equalTo(InvitationCodes.InviteeIsRequired));
    }


    @Scenario("如果邀请加入的团队并不存在，则不允许发送邀请")
    @Test
    public void NotAllowedIfTeamIsNotExist() throws Exception {
        given().header("userName", userName)
                .body("{\"invitee\":\"invitee-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/members/invitation")
                .then()
                .statusCode(400)
                .body("code", equalTo(InvitationCodes.TEAM_IS_NOT_EXISTS.code()))
                .body("message", equalTo(InvitationCodes.TEAM_IS_NOT_EXISTS.message()));
    }

    @Scenario("如果被邀请人不存在，则不允许发送邀请")
    @Test
    public void NotAllowedIfInviteeIsNotExist() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,author) VALUES ('foo-team-Id','team-name','someone')");

        given().header("userName", userName)
                .body("{\"invitee\":\"invitee-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/members/invitation")
                .then()
                .statusCode(400)
                .body("code", equalTo(InvitationCodes.INVITEE_IS_NOT_EXISTS.code()))
                .body("message", equalTo(InvitationCodes.INVITEE_IS_NOT_EXISTS.message()));
    }

    @Scenario("如果邀请人并非团队的成员则不允许发送邀请")
    @Test
    public void NotAllowedIfInviterIsNotAMemberOfTheTeamExist() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,author) VALUES ('foo-team-Id','team-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','someone','password')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('invitee-user-id','766191920@qq.com','invitee-user','password')");

        given().header("userName", userName)
                .body("{\"invitee\":\"invitee-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/members/invitation")
                .then()
                .statusCode(400)
                .body("code", equalTo(InvitationCodes.INVITER_IS_NOT_A_MEMBER_OF_THE_TEAM.code()))
                .body("message", equalTo(InvitationCodes.INVITER_IS_NOT_A_MEMBER_OF_THE_TEAM.message()));
    }

    @Scenario("如果此前已经存在相同的邀请，则取消之前的邀请")
    @Test
    public void cancelPreviousInvitationBeforeSendingNewInvitation() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,author) VALUES ('foo-team-Id','team-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_team_member_invitation (id,team_id,inviter,invitee) VALUES ('foo-invitation-Id','foo-team-Id','someone','invitee-user')");
        jdbcTemplate.execute("INSERT INTO  kb_team_members (id,team_id,member,author) VALUES ('foo-team-member-id','foo-team-Id','someone','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','someone','password')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('invitee-user-id','766191920@qq.com','invitee-user','password')");

        given().header("userName", userName)
                .body("{\"invitee\":\"invitee-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/members/invitation")
                .then()
                .statusCode(201)
                .body("invitee", equalTo("invitee-user"))
                .body("_links.self.href", equalTo("http://localhost:8007/teams/foo-team-Id/members/invitation"))
                .body("_links.members.href", equalTo("http://localhost:8007/teams/foo-team-Id/members"));
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_team_member_invitation where id='foo-invitation-Id' AND delete_status=1").size());
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_team_member_invitation where team_id='foo-team-Id' AND invitee='invitee-user'").size());
    }

    @Scenario("邀请发出后，用户的消息中心也会收到相应的提示")
    @Test
    public void addNotificationAfterSendingInvitation() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_team (id,name,author) VALUES ('foo-team-Id','team-name','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_team_members (id,team_id,member,author) VALUES ('foo-team-member-id','foo-team-Id','someone','someone')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','someone','password')");
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('invitee-user-id','766191920@qq.com','invitee-user','password')");

        given().header("userName", userName)
                .body("{\"invitee\":\"invitee-user\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/teams/foo-team-Id/members/invitation")
                .then()
                .statusCode(201)
                .body("invitee", equalTo("invitee-user"))
                .body("_links.self.href", equalTo("http://localhost:8007/teams/foo-team-Id/members/invitation"))
                .body("_links.members.href", equalTo("http://localhost:8007/teams/foo-team-Id/members"));
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_notification where type='team_member_invitation' AND receiver='invitee-user'").size());
        assertEquals(1, jdbcTemplate.queryForList("select count(*) from kb_team_member_invitation where team_id='fooId' AND invitee='invitee-user'").size());
    }
}
