package org.thiki.kanban.notification;

import org.springframework.cache.annotation.CacheEvict;
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

    @CacheEvict(value = "notification", key = "{'notifications'+#notification.receiver,#userName+unreadNotificationTotal}", allEntries = true)
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
    @CacheEvict(value = "notification", key = "{'notifications'+#notification.receiver,#userName+unreadNotificationTotal}", allEntries = true)
    public Notification findNotificationById(String id) {
        Notification notification = notificationPersistence.read(id);
        if (!notification.getIsRead()) {
            notification.setIsRead(true);
            notificationPersistence.setAlreadyRead(id);
        }
        return notification;
    }
}
