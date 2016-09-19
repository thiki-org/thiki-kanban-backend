package org.thiki.kanban.notification;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xutao on 09/12/16.
 */
@Service
public class NotificationService {

    @Resource
    private NotificationPersistence notificationPersistence;

    public Notification notify(final Notification notification) {
        notificationPersistence.create(notification);
        return notificationPersistence.read(notification.getId());
    }

    public Integer loadUnreadNotificationTotal(String userName) {
        return notificationPersistence.loadUnreadNotificationTotal(userName);
    }

    public List<Notification> loadNotifications(String userName) {
        List<Notification> notificationList = notificationPersistence.loadNotificationsByUserName(userName);
        return notificationList;
    }

    public Notification findNotificationById(String id) {
        Notification notification = notificationPersistence.read(id);
        if (!notification.getIsRead()) {
            notificationPersistence.setAlreadyRead(id);
        }
        return notification;
    }
}
