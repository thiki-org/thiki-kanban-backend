package org.thiki.kanban.notification;

import com.alibaba.fastjson.JSON;
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
    private String title;

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
        return sender == null ? "" : sender;
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
        return JSON.toJSONString(this);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Notification copy() {
        Notification notification = new Notification();
        notification.setContent(this.content);
        notification.setReceiver(this.receiver);
        notification.setSender(this.sender);
        notification.setType(this.type);
        notification.setTitle(this.title);
        return notification;
    }
}
