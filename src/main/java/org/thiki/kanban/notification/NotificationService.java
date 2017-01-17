package org.thiki.kanban.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xutao on 09/12/16.
 */
@Service
public class NotificationService {
    private final static Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Resource
    private NotificationPersistence notificationPersistence;

    @CacheEvict(value = "notification", key = "{'notifications'+#notification.receiver,#userName+unreadNotificationTotal}", allEntries = true)
    public Notification notify(final Notification notification) {
        logger.info("Adding notification:{}", notification);
        notificationPersistence.create(notification);
        return notificationPersistence.read(notification.getId());
    }

    public Integer loadUnreadNotificationTotal(String userName) {
        logger.info("Loading unread notification total.userName:{}", userName);
        return notificationPersistence.loadUnreadNotificationTotal(userName);
    }

    public List<Notification> loadNotifications(String userName) {
        logger.info("Loading notifications by userName userName:{}", userName);
        List<Notification> notificationList = notificationPersistence.loadNotificationsByUserName(userName);
        logger.info("Loaded notifications:{}", notificationList);
        return notificationList;
    }

    @CacheEvict(value = "notification", key = "{'notifications'+#notification.receiver,#userName+unreadNotificationTotal}", allEntries = true)
    public Notification findNotificationById(String id) {
        logger.info("Loading notification id:{}", id);
        Notification notification = notificationPersistence.read(id);
        if (!notification.getIsRead()) {
            notification.setIsRead(true);
            notificationPersistence.setAlreadyRead(id);
        }
        logger.info("Loaded notification notification:{}", notification);
        return notification;
    }
}
