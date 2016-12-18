package org.thiki.kanban.notification;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubt on 7/5/16.
 */
@RestController
public class NotificationController {
    @Resource
    public NotificationService notificationService;
    @Resource
    private NotificationResource notificationResource;
    @Resource
    NotificationsResource notificationsResource;
    @Resource
    private UnreadNotificationsResource unreadNotificationsResource;

    @RequestMapping(value = "/{userName}/notifications/unread/total", method = RequestMethod.GET)
    public HttpEntity loadUnreadNotificationsTotal(@PathVariable("userName") String userName) throws Exception {
        Integer unreadNotificationTotal = notificationService.loadUnreadNotificationTotal(userName);

        return Response.build(unreadNotificationsResource.toResource(userName, unreadNotificationTotal));
    }

    @RequestMapping(value = "/{userName}/notifications", method = RequestMethod.GET)
    public HttpEntity loadNotifications(@PathVariable("userName") String userName) throws Exception {
        List<Notification> notifications = notificationService.loadNotifications(userName);

        return Response.build(notificationsResource.toResource(userName, notifications));
    }

    @RequestMapping(value = "/{userName}/notifications/{id}", method = RequestMethod.GET)
    public HttpEntity loadNotificationById(@PathVariable("id") String id, @PathVariable("userName") String userName) throws Exception {
        Notification notification = notificationService.findNotificationById(id);
        return Response.build(notificationResource.toResource(userName, notification));
    }
}
