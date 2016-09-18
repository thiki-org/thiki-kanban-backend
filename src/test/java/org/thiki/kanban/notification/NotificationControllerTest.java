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
                .get("/users/someone/notifications/unread/total")
                .then()
                .statusCode(200)
                .body("unreadNotificationsTotal", equalTo(1))
                .body("_links.self.href", equalTo("http://localhost:8007/users/someone/notifications/unread/total"))
                .body("_links.notifications.href", equalTo("http://localhost:8007/users/someone/notifications"));
    }

    @Scenario("获取所有消息>用户登录后,可以在消息中心查看所有消息,以便及时处理未读消息或重新查看已读消息")
    @Test
    public void loadAllNotifications() throws Exception {
        dbPreparation.table("kb_notification")
                .names("id,receiver,sender,content,type,link")
                .values("foo-notification-id", "someone", "sender@gmail.com", "content", "notificationType", "http://hello.com").exec();

        given().header("userName", userName)
                .when()
                .get("/users/someone/notifications")
                .then()
                .statusCode(200)
                .body("notifications[0].sender", equalTo("sender@gmail.com"))
                .body("notifications[0].content", equalTo("content"))
                .body("notifications[0].link", equalTo("http://hello.com"))
                .body("notifications[0].isRead", equalTo(false))
                .body("notifications[0]._links.self.href", equalTo("http://localhost:8007/notifications/foo-notification-id"))
                .body("notifications[0]._links.notifications.href", equalTo("http://localhost:8007/users/someone/notifications"))
                .body("_links.self.href", equalTo("http://localhost:8007/users/someone/notifications"));
    }
}
