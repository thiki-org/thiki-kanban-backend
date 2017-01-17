package org.thiki.kanban.notification;

import org.thiki.kanban.foundation.common.date.DateUtil;

import java.io.Serializable;

/**
 * Created by xutao on 09/12/16.
 */
public class Notification implements Serializable {

    private String id;

    private String receiver;

    private String sender;

    private String content;

    private String link;

    private boolean isRead;

    private String creationTime;
    private String displayTime;
    private String type;
    private String typeName;
    private String modificationTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDisplayTime() {
        return DateUtil.showTime(this.creationTime);
    }

    public String getTypeName() {
        return NotificationType.getNameByType(this.type);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", link='" + link + '\'' +
                ", isRead=" + isRead +
                ", creationTime='" + creationTime + '\'' +
                ", displayTime='" + displayTime + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                '}';
    }
}
