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

    @Scenario("获取未读消息数-用户登录后，可以获取未读消息数量，以便在醒目位置显示提醒用户及时处理")
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
}
