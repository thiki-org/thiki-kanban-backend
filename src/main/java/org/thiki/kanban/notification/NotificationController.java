package org.thiki.kanban.notification;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by xubt on 7/5/16.
 */
@RestController
public class NotificationController {
    @Resource
    public NotificationService notificationService;

    @RequestMapping(value = "/users/{userName}/notifications/unread/total", method = RequestMethod.GET)
    public HttpEntity loadUnreadNotificationsTotal(@PathVariable("userName") String userName) throws Exception {
        Notifications notifications = notificationService.loadUnreadNotificationTotal(userName);

        return Response.build(new NotificationsResource(userName, notifications));
    }

    @RequestMapping(value = "/users/{userName}/notifications", method = RequestMethod.GET)
    public HttpEntity loadNotifications(@PathVariable("userName") String userName) {
        return null;
    }
}
