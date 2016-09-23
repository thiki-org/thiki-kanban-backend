package org.thiki.kanban.notification;

/**
 * Created by xubt on 23/09/2016.
 */
public enum NotificationType {
    TEAM_MEMBER_INVITATION("team_member_invitation", "团队邀请");
    private String type;
    private String name;

    NotificationType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String type() {
        return type;
    }

    public String typeName() {
        return name;
    }
}
