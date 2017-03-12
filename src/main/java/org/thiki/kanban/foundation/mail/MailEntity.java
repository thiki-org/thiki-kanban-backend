package org.thiki.kanban.foundation.mail;

import org.thiki.kanban.notification.Notification;
import org.thiki.kanban.notification.NotificationType;

/**
 * Created by xubt on 8/14/16.
 */
public class MailEntity {
    private String userName;
    private String receiver;
    private String sender;
    private String subject;
    private String content;

    private String dateline;
    private String templateName;
    private String senderNickName;
    private String receiverNickName;

    private NotificationType notificationType;
    private String receiverUserName;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSenderNickName() {
        return senderNickName;
    }

    public void setSenderNickName(String senderUserName) {
        this.senderNickName = senderUserName;
    }

    public String getReceiverNickName() {
        return receiverNickName;
    }

    public void setReceiverNickName(String receiverUserName) {
        this.receiverNickName = receiverUserName;
    }

    public Notification newNotification() {
        Notification notification = new Notification();
        notification.setTitle(this.getSubject());
        notification.setReceiver(this.receiverUserName);
        notification.setSender(this.sender);
        notification.setContent(this.content);
        notification.setType(notificationType.type());
        return notification;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }
}
