package org.thiki.kanban.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.mail.MailEntity;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.user.Profile;
import org.thiki.kanban.user.UsersService;

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
    @Resource
    private MailService mailService;
    @Resource
    private UsersService usersService;

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

    public void sendEmailAfterNotifying(MailEntity mailEntity) throws Exception {
        Notification notification = mailEntity.newNotification();
        logger.info("Sending email after notifying notification:{},mailEntity:{}", notification, mailEntity);
        notify(notification);
        mailService.sendMailByTemplate(mailEntity);
    }

    public void sendEmailAfterNotifying(MailEntity mailEntity, String verificationFailedEmailTemplate, List<String> receiverUserNames) throws Exception {
        Notification notification = mailEntity.newNotification();
        logger.info("Sending email after notifying notification:{},mailEntity:{}", notification, mailEntity);
        for (String receiverUserName : receiverUserNames) {
            Notification notificationNew = notification.copy();
            notificationNew.setReceiver(receiverUserName);
            notify(notificationNew);
            Profile receiver = usersService.loadProfileByUserName(receiverUserName);
            mailEntity.setReceiverEmailAddress(receiver.getEmail());
            mailEntity.setReceiverNickName(receiver.getNickName());
            mailEntity.setTemplateName(verificationFailedEmailTemplate);
            mailService.sendMailByTemplate(mailEntity);
        }
    }
}
