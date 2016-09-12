package org.thiki.kanban.notification;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xutao on 09/12/16.
 */
@Service
public class NotificationService {

    @Resource
    private NotificationPersistence notificationPersistence;

    public Notification notify(final Notification notification) {
        notificationPersistence.create(notification);
        return notificationPersistence.findById(notification.getId());
    }
}
