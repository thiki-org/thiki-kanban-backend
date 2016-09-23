package org.thiki.kanban.notification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Created by xutao on 9/12/16.
 */
@Domain(order = DomainOrder.NOTIFICATION, name = "消息")
@RunWith(SpringJUnit4ClassRunner.class)
public class NotificationControllerTest extends TestBase {

    @Scenario("获取未读消息数>用户登录后,可以获取未读消息数量,以便在醒目位置显示提醒用户及时处理")
    @Test
    public void loadUnreadNotificationAfterLoading() throws Exception {
        dbPreparation.table("kb_notification")
                .names("id,receiver,sender,content,type")
                .values("foo-notification-id", "someone", "sender@gmail.com", "content", "notificationType").exec();

        given().header("userName", userName)
                .when()
                .get("/someone/notifications/unread/total")
                .then()
                .statusCode(200)
                .body("unreadNotificationsTotal", equalTo(1))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/notifications/unread/total"))
                .body("_links.notifications.href", equalTo("http://localhost:8007/someone/notifications"));
    }

    @Scenario("获取所有消息>用户登录后,可以在消息中心查看所有消息,以便及时处理未读消息或重新查看已读消息")
    @Test
    public void loadAllNotifications() throws Exception {
        dbPreparation.table("kb_notification")
                .names("id,receiver,sender,content,type,link")
                .values("foo-notification-id", "someone", "sender@gmail.com", "content", NotificationType.TEAM_MEMBER_INVITATION.type(), "http://hello.com").exec();

        given().header("userName", userName)
                .when()
                .get("/someone/notifications")
                .then()
                .statusCode(200)
                .body("notifications[0].sender", equalTo("sender@gmail.com"))
                .body("notifications[0].content", equalTo("content"))
                .body("notifications[0].link", equalTo("http://hello.com"))
                .body("notifications[0].isRead", equalTo(false))
                .body("notifications[0].type", equalTo(NotificationType.TEAM_MEMBER_INVITATION.type()))
                .body("notifications[0].typeName", equalTo(NotificationType.TEAM_MEMBER_INVITATION.typeName()))
                .body("notifications[0]._links.self.href", equalTo("http://localhost:8007/someone/notifications/foo-notification-id"))
                .body("notifications[0]._links.notifications.href", equalTo("http://localhost:8007/someone/notifications"))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/notifications"));
    }

    @Scenario("获取指定消息>用户可以在消息中心查看某条具体的消息,查看完毕后将该条消息设置为已读")
    @Test
    public void loadNotificationByID() throws Exception {
        dbPreparation.table("kb_notification")
                .names("id,receiver,sender,content,type,link")
                .values("foo-notification-id", "someone", "sender@gmail.com", "content", "notificationType", "http://hello.com").exec();

        given().header("userName", userName)
                .when()
                .get("/someone/notifications/foo-notification-id")
                .then()
                .statusCode(200)
                .body("sender", equalTo("sender@gmail.com"))
                .body("content", equalTo("content"))
                .body("link", equalTo("http://hello.com"))
                .body("isRead", equalTo(true))
                .body("displayTime", notNullValue())
                .body("_links.self.href", equalTo("http://localhost:8007/someone/notifications/foo-notification-id"))
                .body("_links.notifications.href", equalTo("http://localhost:8007/someone/notifications"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_notification WHERE is_read=1 AND ID='foo-notification-id'").size());
    }

    @Scenario("获取指定消息>查看某条具体的消息时，如果该消息已读，则加载后不必再设置为已读")
    @Test
    public void notSetNotificationReadIfItHasAlreadyBeenReadAfterLoading() throws Exception {
        dbPreparation.table("kb_notification")
                .names("id,receiver,sender,content,type,link,is_read")
                .values("foo-notification-id", "someone", "sender@gmail.com", "content", "notificationType", "http://hello.com", 1).exec();

        given().header("userName", userName)
                .when()
                .get("/someone/notifications/foo-notification-id")
                .then()
                .statusCode(200)
                .body("sender", equalTo("sender@gmail.com"))
                .body("content", equalTo("content"))
                .body("link", equalTo("http://hello.com"))
                .body("isRead", equalTo(true))
                .body("_links.self.href", equalTo("http://localhost:8007/someone/notifications/foo-notification-id"))
                .body("_links.notifications.href", equalTo("http://localhost:8007/someone/notifications"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_notification WHERE is_read=1 AND ID='foo-notification-id'").size());
    }
}
